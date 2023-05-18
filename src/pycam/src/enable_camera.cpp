#include "ros/ros.h"
#include "ros/console.h"
#include <image_transport/image_transport.h>
#include <opencv2/opencv.hpp>
#include <cv_bridge/cv_bridge.h>
#include <sensor_msgs/CameraInfo.h>

int main(int argc, char** argv) {
    ros::init(argc, argv, "webcam_publish_test");
    ros::NodeHandle nh;

    image_transport::ImageTransport it(nh);
    image_transport::Publisher pub = it.advertise("camera/image_color", 1);

    // Adding CameraInfo publisher
    ros::Publisher cam_info_pub = nh.advertise<sensor_msgs::CameraInfo>("camera/camera_info", 1);
    ros::Rate rate(120);

    sensor_msgs::CameraInfo cam_info_msg;

    // Camera intrinsic parameters
    cam_info_msg.K = {385.172671, 0., 319.493898, 0., 516.679620, 231.445904, 0., 0., 1.};

    // Distortion coefficients
    cam_info_msg.D = {-0.311362, 0.082442, 0.000836, 0.001488};

    // Projection matrix parameters
    cam_info_msg.P = {84.19519, 0., 321.97906, 0., 0., 477.86301, 229.88387, 0., 0., 0., 1., 0.};

    //Rotation Matrix
    cam_info_msg.R = {1., 0., 0., 0., 1., 0., 0., 0., 1.};

    // Camera distortion model
    cam_info_msg.distortion_model = "plumb_bob";

    // Camera frame id
    cam_info_msg.header.frame_id = "camera";

    // Image dimensions
    cam_info_msg.height = 480;
    cam_info_msg.width = 640;
    

    // Define the video size
    cv::Size size = cv::Size(640, 480);
    // Initialize video capture
    cv::VideoCapture cap("nvarguscamerasrc ! video/x-raw(memory:NVMM), width=(int)640, height=(int)480,format=(string)NV12, framerate=(fraction)120/1 ! nvvidconv ! video/x-raw, format=(string)BGRx ! videoconvert !  appsink", cv::CAP_GSTREAMER);
    
    if (!cap.isOpened()) {
        ROS_ERROR("Could not open camera");
        return -1;  
    } 

    // Matrix to store each frame
    cv::Mat frame;
    
    // Main loop
    while (ros::ok()) {
        // Capture a frame
        cap >> frame;
        
        // If frame is valid
        if (!frame.empty()) {
            // Convert the frame to a ROS image message
            sensor_msgs::ImagePtr msg = cv_bridge::CvImage(std_msgs::Header(), "bgr8", frame).toImageMsg();
            // Publish the message
            pub.publish(msg);
            // Publish camera info
            cam_info_msg.header.stamp = ros::Time::now();
            cam_info_pub.publish(cam_info_msg);
        }

        rate.sleep();
    }

    // Release the video capture object
    cap.release();
    return 0;
}