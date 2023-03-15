#!/usr/bin/env python3

import rospy
from sensor_msgs.msg import LaserScan
from geometry_msgs.msg import Twist, Pose
from nav_msgs.msg import Odometry
from math import atan2, sqrt, pi, tan
from tf.transformations import euler_from_quaternion
from puzzlebot_sim.srv import SetGoal, SetGoalResponse
from std_msgs.msg import Bool

class grabMachine:
    
    def __init__(self):
        rospy.init_node('grabbing_algorithm', anonymous=True)
        self.grab_pub = rospy.Publisher('/grip', Bool, queue_size=10)
        self.cmd_pub = rospy.Publisher('/cmd_vel', Twist, queue_size=10)
        self.aruco_sub = rospy.Subscriber('/look', Bool, self.lookCallback)
        
        
        self.rate = rospy.Rate(self.RATE)
        
    
    def lookCallback(self, look):
        if(look):
            self.look4aruco
            
    def look4aruco(self):
        vel = Twist()
        while(self.cubeDetect == 0):
            vel.angular.z = 0.2
            self.set_robot_speed(vel)
            
        vel.angular.z = 0
        self.set_robot_speed(vel)
        self.go2aruco
        
    def go2aruco(self):
        vel = Twist()
        offsetAng = actAng-arucoAng
        offsetDist = actAng-arucoAng
        
        #correct angle
        while(offsetAng > 0.1):
            vel.angular.z = 0.2
            self.set_robot_speed(vel)
        vel.angular.z = 0
        self.set_robot_speed(vel)
        
        #correct distance
        while(offsetDist > 0.1):
            vel.linear.x = 0.2
            self.set_robot_speed(vel)
        vel.linear.x = 0
        self.set_robot_speed(vel)
 
    # Function to set the robot's speed
    def set_robot_speed(self, cmd_vel: Twist):
        # Calculate the desired speed change
        speed_change = cmd_vel.linear.x - self.current_speed

        # If the desired speed change exceeds the maximum, limit it
        if abs(speed_change) > self.MAX_SPEED_CHANGE:
            speed_change = self.MAX_SPEED_CHANGE if speed_change > 0 else -self.MAX_SPEED_CHANGE

        angular_speed_change = cmd_vel.angular.z - self.current_angular_speed
        # If the desired speed change exceeds the maximum, limit it
        if abs(angular_speed_change) > self.MAX_SPEED_CHANGE:
            angular_speed_change = self.MAX_SPEED_CHANGE if angular_speed_change > 0 else -self.MAX_SPEED_CHANGE

        # Update the current speed
        self.current_speed += speed_change
        self.current_angular_speed += angular_speed_change
        cmd_vel.linear.x = self.current_speed
        cmd_vel.angular.z = self.current_angular_speed

        self.cmd_pub.publish(cmd_vel)
        
        
    
# Main function to start the grabbing algorithm    
if __name__ == '__main__':
    grab = grabMachine()
    rate = rospy.Rate(30)
    rospy.spin()