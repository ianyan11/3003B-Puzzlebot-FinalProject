#include "ros/ros.h"
#include "ros/console.h"
#include <opencv2/opencv.hpp>
#include <cv_bridge/cv_bridge.h>
#include <aruco_ros/aruco_ros_utils.h>
int main(int argc, char** argv) {
    ros::init(argc, argv, "webcam_publish_test");
    ros::NodeHandle nh;
    ros::Publisher pub = nh.advertise<sensor_msgs::Image>("camera/image", 1);
    ros::Publisher marker_pub = nh.advertise<sensor_msgs::Image>("camera/marker_image", 1);
    ros::Rate rate(60);
    cv::VideoCapture cap(
        "nvarguscamerasrc ! video/x-raw(memory:NVMM), width=(int)1920, height=(int)1080,format=(string)NV12, framerate=(fraction)30/1 ! nvvidconv ! video/x-raw, format=(string)BGRx ! videoconvert !  appsink", 
        cv::CAP_GSTREAMER);
    bool stuff = cap.set(cv::CAP_PROP_FPS, 30);
    aruco::CameraParameters camParam = aruco::CameraParameters();
    cv::Mat frame, frame2;
    aruco::MarkerDetector mDetector;
    float min_marker_size; // percentage of image area
    nh.param<float>("min_marker_size", min_marker_size, 0.01);
    std::vector<aruco::Marker> markers;
    mDetector.setDetectionMode(aruco::DM_VIDEO_FAST, min_marker_size);
    while (ros::ok()) {
        markers.clear();
        cap >> frame;
        if (frame.rows) {
            // resize image to 640x480
            cv::resize(frame, frame, cv::Size(640, 480));
             //make frame sharper
            cv::GaussianBlur(frame, frame2, cv::Size(0, 0), 3);
            cv::addWeighted(frame, 1.5, frame2, -0.6, 0, frame);
            cv::resize(frame, frame2, cv::Size(320, 240));
            // detect markers
            sensor_msgs::ImagePtr msg = cv_bridge::CvImage(std_msgs::Header(), "bgr8", frame2).toImageMsg();
            pub.publish(msg);
            mDetector.detect(frame, markers, camParam);
              // for each marker, draw info and its boundaries in the image
            for (std::size_t i = 0; i < markers.size(); ++i)
            {
                markers[i].draw(frame, cv::Scalar(0, 0, 255), 2);
                
                //for (std::size_t i = 0; i < markers.size(); ++i)
                //{
                //    aruco::CvDrawingUtils::draw3dAxis(frame, markers[i], camParam);
                //}
            }
            if(markers.size() > 0)
            {
                cv::resize(frame, frame2, cv::Size(320, 240));
                marker_pub.publish(cv_bridge::CvImage(std_msgs::Header(), "bgr8", frame2).toImageMsg());
            }
        }
        rate.sleep();
    }
    cap.release();
    return 0;
}