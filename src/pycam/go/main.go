package main

import (
	"context"
	"log"
	"net/http"

	"google.golang.org/grpc"
	"github.com/grpc-ecosystem/grpc-gateway/v2/runtime"

	pb "positionGate/positionGateway"
)

func main() {
	ctx := context.Background()
	mux := runtime.NewServeMux()

	// Create a gRPC connection to your existing Python gRPC server
	grpcServerEndpoint := "localhost:50051" // Replace with the actual address of your Python gRPC server
	opts := []grpc.DialOption{grpc.WithInsecure()}
	err := pb.RegisterPositionServiceHandlerFromEndpoint(ctx, mux, grpcServerEndpoint, opts)
	if err != nil {
		log.Fatalf("failed to register gateway: %v", err)
	}

	// Start the HTTP server
	httpAddr := ":8080" // Set the listening address for the gateway server
	log.Printf("gRPC gateway server listening on %s", httpAddr)
	if err := http.ListenAndServe(httpAddr, mux); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
