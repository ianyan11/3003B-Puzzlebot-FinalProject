#!/usr/bin/env python3


"""
@file grpc_wrapper.py
@author Ian De La Garza
@date 28 de mayo de 2023
@brief Este archivo contiene la clase PositionServiceServicer que 
sirve como servidor para enviar datos de posición a través de gRPC.
"""

import time
from concurrent import futures

import grpc
import rospy
from geometry_msgs.msg import PointStamped

import PositionService_pb2
import PositionService_pb2_grpc

class PositionServiceServicer(PositionService_pb2_grpc.PositionServiceServicer):
    """
    @brief Servidor gRPC que transmite datos de posición.
    """
    def __init__(self):
        """
        @brief Inicializa el nodo ROS, se suscribe a "/camera/marker_center" 
        y configura el servidor gRPC.
        """
        ## @brief Ultimo mensaje de posición recibido.
        self.latest_message = PointStamped()
        rospy.init_node('position_listener', anonymous=True)

        ## @brief Callback para el suscriptor ROS.
        rospy.Subscriber("/camera/marker_center", PointStamped, self.callback)
        ## @brief Servidor gRPC.
        self.server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))


    def callback(self, data):
        """
        @brief Callback para el suscriptor ROS, actualiza el mensaje más reciente.

        @param data El nuevo mensaje de posición a guardar.
        """

        self.latest_message = data

    def SendPosition(self, empty, context):
        """
        @brief Envía los datos de posición actuales a través de gRPC.

        @param empty El objeto vacío enviado por el cliente, no se utiliza.
        @param context El contexto gRPC, no se utiliza.

        @return Una respuesta de posición que contiene los datos de posición más recientes.
        """
        if self.latest_message is not None:
            response = PositionService_pb2.Position()
            response.x = self.latest_message.point.x
            response.y = self.latest_message.point.y
            response.timestamp = self.latest_message.header.stamp.nsecs
            return response
        else:
            raise grpc.RpcError("No position data received yet")

    def serve(self):
        """
        @brief Inicia el servidor gRPC.
        """
        PositionService_pb2_grpc.add_PositionServiceServicer_to_server(self, self.server)
        self.server.add_insecure_port('[::]:50051')
        self.server.start()

    def serverStop(self):
        """
        @brief Detiene el servidor gRPC.
        """
        self.server.stop(0)
    


if __name__ == '__main__':
    """
    @brief El punto de entrada del programa. 
    Se crea una instancia de PositionServiceServicer y se inicia el servidor gRPC.
    Cuando se interrumpe el programa (p.ej., se presiona Ctrl+C), se detiene el servidor gRPC.
    """

    ## @brief Instancia del servidor gRPC.
    servicer = PositionServiceServicer()
    servicer.serve()
    try:
        rospy.spin()
    except KeyboardInterrupt:
        servicer.serverStop()
