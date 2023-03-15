#!/usr/bin/env python3

import rospy
#from sensor_msgs.msg import LaserScan
from geometry_msgs.msg import Twist, Pose
from nav_msgs.msg import Odometry
from math import atan2, sqrt, pi, tan
from tf.transformations import euler_from_quaternion
from puzzlebot_control.srv import SetGoal, SetGoalResponse

class Go2Goal:
    # Define class-level constants
    RATE = 60 # Hz
    MAX_TURN_SPEED = 0.5 # rad/s
    KP = 0.75 # Proportional gain for turning
    GOAL_REACHED_THRESHOLD = 0.4  # the goal is considered reached if we are closer than this value

    # Initialize ROS node, subscribers, and publishers
    def __init__(self):
        # Initialize ROS node
        rospy.init_node('go2goal', anonymous=True)

        # Subscribers
        self.robot_odom = rospy.Subscriber('/odom', Odometry, self.odom_callback)
        # Publishers
        self.cmd_pub = rospy.Publisher('/cmd_vel', Twist, queue_size=10)
        # Service
        self.goal_service = rospy.Service('/puzzlebot_setGoal', SetGoal, self.set_goal_service_callback)

        self.rate = rospy.Rate(self.RATE)

        self.goal_direction = 0.0 # Direction towards goal in RADIANS in world frame
        self.goal_direction_at_obstacle = 0.0 # Direction towards goal in RADIANS in world frame when obstacle was detected
        self.goal_distance = 0.0 # Distance to goal in METERSs
        self.goal = None 
        self.robot_position = Pose() 
        self.current_speed = 0.0
        self.robot_orientation = 0.0 # Robot orientation in RADIANS in world frame
        # Calculate the maximum allowed speed change based on the acceleration limit
        MAX_ACCELERATION = 0.3 # m/s^2
        self.MAX_SPEED_CHANGE = MAX_ACCELERATION / self.RATE  # Assumes rate is in Hz

    # Callback function to process Odometry messages
    def odom_callback(self, msg: Odometry):
        self.robot_position = msg.pose.pose
        orientation_q = msg.pose.pose.orientation
        orientation_list = [orientation_q.x, orientation_q.y, orientation_q.z, orientation_q.w]
        _, _, yaw = euler_from_quaternion(orientation_list)
        self.robot_orientation = self.normalize_angle(yaw)  # You'll need to add this new field
        if self.goal is not None:
            self.calculate_goal_direction()
    
    # Function to calculate the direction towards the goal
    def calculate_goal_direction(self):
        if self.goal is None:
            return
        # Calculate direction towards goal
        delta_x = self.goal.x - self.robot_position.position.x
        delta_y = self.goal.y - self.robot_position.position.y
        self.goal_direction = atan2(delta_y, delta_x)
        self.goal_distance = sqrt(delta_x**2 + delta_y**2)

    # Callback function to process goal setting service requests
    def set_goal_service_callback(self, req):
        self.goal = req.goal
        # Start the bug2 algorithm towards the new goal
        completed = self.run()
        return SetGoalResponse(completed)
    
    # Function to check if the robot has crossed the line from the obstacle encounter point to the goal
    def has_crossed_line(self, x1, y1, x2, y2, angle):
        m = tan(angle)  # calculate slope of the line
        return (y2 - y1) > m * (x2 - x1)

    # Function to make the robot move towards the goal
    def move_towards_goal(self):
        cmd_vel = Twist()
        cmd_vel.linear.x = 0.1
        cmd_vel.angular.z = self.goal_direction - self.robot_orientation
        self.set_robot_speed(cmd_vel)

    # Function to set the robot's speed
    def set_robot_speed(self, cmd_vel: Twist):
        # Calculate the desired speed change
        speed_change = cmd_vel.linear.x - self.current_speed

        # If the desired speed change exceeds the maximum, limit it
        if abs(speed_change) > self.MAX_SPEED_CHANGE:
            speed_change = self.MAX_SPEED_CHANGE if speed_change > 0 else -self.MAX_SPEED_CHANGE

        # Update the current speed
        self.current_speed += speed_change
        cmd_vel.linear.x = self.current_speed

        self.cmd_pub.publish(cmd_vel)

    # Function to normalize an angle to the range [-pi, pi]
    def normalize_angle(self, angle):
        while angle > pi:
            angle -= 2.0 * pi
        while angle < -pi:
            angle += 2.0 * pi
        return angle
    
    # Main function to run the Bug2 algorithm
    def run(self):
        # Main loop
        while not rospy.is_shutdown():
            if self.goal_distance < self.GOAL_REACHED_THRESHOLD:
                cmdvel = Twist()
                cmdvel.linear.x = 0
                cmdvel.angular.z = 0
                self.cmd_pub.publish(cmdvel)
                rospy.loginfo("Goal reached!")
                return True
            else:
                self.move_towards_goal()
            self.rate.sleep()

# Main function to start the Bug2 algorithm    
if __name__ == '__main__':
    bug2 = Go2Goal()
    rospy.spin()