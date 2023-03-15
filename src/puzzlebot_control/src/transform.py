#!/usr/bin/env python3
import rospy
import math
from std_msgs.msg import Float32
from std_srvs.srv import Empty
from geometry_msgs.msg import Twist, Pose, TransformStamped, Vector3, Quaternion
from nav_msgs.msg import Odometry
from tf2_geometry_msgs import PoseStamped
from tf.transformations import quaternion_from_euler 
from tf2_ros import TransformBroadcaster 

class transformPuzzlebot:
    def __init__(self):
        self.x = 0
        self.y = 0
        
        #self.pub = rospy.Publisher('/pose_sim', PoseStamped, queue_size=10)
        rospy.Subscriber('/odom', Odometry, self.odomCallback)

        self.frequency = 100

    def odomCallback(self, odom: Odometry):
        self.x = odom.pose.pose.position.x
        self.y = odom.pose.pose.position.y
        self.broadcast_transform(odom.pose.pose.orientation)

        #Send transformation using broadcaster
    def broadcast_transform(self, orientation: Quaternion):
        #Initialize broadcaster
        br = TransformBroadcaster()
        t = TransformStamped()
        #Fill the transform with the position and orientations
        t.header.stamp = rospy.Time.now()
        #Frame names
        t.header.frame_id = "rviz_puzzlebot/base_link"
        t.child_frame_id = "rviz_puzzlebot/chassis"
        t.transform.translation = Vector3(self.x, self.y, 0)
        t.transform.rotation = orientation
        #Send transform
        br.sendTransform(t)

def main():
    rospy.init_node('transformNode', anonymous=True)
    tf = transformPuzzlebot()
    rate =  rospy.Rate(100)
    while not rospy.is_shutdown():
        # tf.run()
        rate.sleep()

if (__name__== "__main__") :
    main()
