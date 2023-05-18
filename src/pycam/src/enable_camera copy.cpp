#include "ros/ros.h"
#include "ros/console.h"
#include <geometry_msgs/PointStamped.h>
#include <image_transport/image_transport.h>
#include <opencv2/opencv.hpp>
#include <cv_bridge/cv_bridge.h>
#include <aruco_ros/aruco_ros_utils.h>
#include "pycam/multiply.h"
#include <opencv2/core/core.hpp>
#include <ros/package.h>

int main(int argc, char** argv) {
    ros::init(argc, argv, "webcam_publish_test");
    ros::NodeHandle nh;
    image_transport::ImageTransport it(nh);
    image_transport::Publisher pub = it.advertise("camera/image", 1);
    image_transport::Publisher marker_pub = it.advertise("camera/marker_image", 1);
    ros::Publisher marker_center_pub = nh.advertise<geometry_msgs::PointStamped>("camera/marker_center", 1);
    ros::Rate rate(30);
    cv::VideoCapture cap(
        "nvarguscamerasrc ! video/x-raw(memory:NVMM), width=(int)640, height=(int)480,format=(string)NV12, framerate=(fraction)30/1 ! nvvidconv ! video/x-raw, format=(string)BGRx ! videoconvert !  appsink", 
        cv::CAP_GSTREAMER);
    if (!cap.isOpened()) {
        ROS_ERROR("Could not open camera");
        return -1;  
    } 
    bool success = cap.set(cv::CAP_PROP_FPS, 30);
    if (!success) {
        ROS_WARN("Failed to set camera FPS");
    }    
    cv::Mat camera_matrix, distortion_coefficients;
        // Fill the camera matrix
    camera_matrix = (cv::Mat_<double>(3,3) << 385.172671, 0., 319.493898, 
                                            0., 516.679620, 231.445904,
                                            0., 0., 1.);

    // Fill the distortion coefficients
    distortion_coefficients = (cv::Mat_<double>(1,4) << -0.311362, 0.082442, 0.000836, 0.001488);
    cv::Size size = cv::Size(640, 480);

    aruco::CameraParameters camParam;
    
    camParam.setParams(camera_matrix, distortion_coefficients, size);
    cv::Mat frame, undistorted;
    aruco::MarkerDetector mDetector;
    float min_marker_size; // percentage of image area
    nh.param<float>("min_marker_size", min_marker_size, 0.01);
    std::vector<aruco::Marker> markers;
    mDetector.setDetectionMode(aruco::DM_VIDEO_FAST, min_marker_size);
    while (ros::ok()) {
        markers.clear();
        cap >> frame;
        if (frame.rows) {
            
            cv::undistort(frame, undistorted, camParam.CameraMatrix, camParam.Distorsion);
            mDetector.detect(undistorted, markers, camParam, 0.46, false, true);
            sensor_msgs::ImagePtr msg = cv_bridge::CvImage(std_msgs::Header(), "bgr8", undistorted).toImageMsg();
            // detect markers
            pub.publish(msg);
            // for each marker, draw info and its boundaries in the image
            for (std::size_t i = 0; i < markers.size(); ++i)
            {
                markers[i].draw(undistorted, cv::Scalar(0, 0, 255), 2);
                aruco::CvDrawingUtils::draw3dAxis(frame, markers[i], camParam);
                //get center of marker
                cv::Point2f center = markers[i].getCenter();
                //publish center of marker
                int x = center.x;
                int y = center.y;
                multiply(&x, &y);
                geometry_msgs::PointStamped marker_center;
                marker_center.header.stamp = ros::Time::now();
                marker_center.point.x = x;
                marker_center.point.y = y;
                marker_center_pub.publish(marker_center);

            }
            if(markers.size() > 0)
            {
                marker_pub.publish(cv_bridge::CvImage(std_msgs::Header(), "bgr8", undistorted).toImageMsg());
            }
        }
        rate.sleep();
    }
    cap.release();
    return 0;
}