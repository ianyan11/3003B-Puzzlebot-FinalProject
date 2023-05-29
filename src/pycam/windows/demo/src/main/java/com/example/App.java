package com.example;

import com.google.protobuf.Empty;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import object_position.PositionServiceGrpc;
import object_position.PositionServiceOuterClass.Position;

public class App 
{
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("192.168.55.1:50051")
        .usePlaintext()
        .build();

        // Reemplace 'MyServiceGrpc' y 'MyRequest' con los nombres de tu servicio y tu mensaje.
        PositionServiceGrpc.PositionServiceBlockingStub stub = PositionServiceGrpc.newBlockingStub(channel);
        try {
            while(true) {
                Position response = stub.sendPosition(null);
                System.out.println("Response x: " + response.getX() + "Response y: " + response.getY() + " Timestamp: " + response.getTimestamp());
                Thread.sleep(10);  // Add delay of 1 second between each request
            }
        } catch (InterruptedException e) {
            System.out.println("Shutting down due to user request.");
        } finally {
            channel.shutdown();
        }
    }
}

