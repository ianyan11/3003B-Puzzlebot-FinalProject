package object_position;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.42.1)",
    comments = "Source: PositionService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PositionServiceGrpc {

  private PositionServiceGrpc() {}

  public static final String SERVICE_NAME = "object_position.PositionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<object_position.PositionServiceOuterClass.Empty,
      object_position.PositionServiceOuterClass.Position> getSendPositionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendPosition",
      requestType = object_position.PositionServiceOuterClass.Empty.class,
      responseType = object_position.PositionServiceOuterClass.Position.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<object_position.PositionServiceOuterClass.Empty,
      object_position.PositionServiceOuterClass.Position> getSendPositionMethod() {
    io.grpc.MethodDescriptor<object_position.PositionServiceOuterClass.Empty, object_position.PositionServiceOuterClass.Position> getSendPositionMethod;
    if ((getSendPositionMethod = PositionServiceGrpc.getSendPositionMethod) == null) {
      synchronized (PositionServiceGrpc.class) {
        if ((getSendPositionMethod = PositionServiceGrpc.getSendPositionMethod) == null) {
          PositionServiceGrpc.getSendPositionMethod = getSendPositionMethod =
              io.grpc.MethodDescriptor.<object_position.PositionServiceOuterClass.Empty, object_position.PositionServiceOuterClass.Position>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendPosition"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  object_position.PositionServiceOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  object_position.PositionServiceOuterClass.Position.getDefaultInstance()))
              .setSchemaDescriptor(new PositionServiceMethodDescriptorSupplier("SendPosition"))
              .build();
        }
      }
    }
    return getSendPositionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PositionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PositionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PositionServiceStub>() {
        @java.lang.Override
        public PositionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PositionServiceStub(channel, callOptions);
        }
      };
    return PositionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PositionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PositionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PositionServiceBlockingStub>() {
        @java.lang.Override
        public PositionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PositionServiceBlockingStub(channel, callOptions);
        }
      };
    return PositionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PositionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PositionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PositionServiceFutureStub>() {
        @java.lang.Override
        public PositionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PositionServiceFutureStub(channel, callOptions);
        }
      };
    return PositionServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The service definition.
   * </pre>
   */
  public static abstract class PositionServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sendPosition(object_position.PositionServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<object_position.PositionServiceOuterClass.Position> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendPositionMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendPositionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                object_position.PositionServiceOuterClass.Empty,
                object_position.PositionServiceOuterClass.Position>(
                  this, METHODID_SEND_POSITION)))
          .build();
    }
  }

  /**
   * <pre>
   * The service definition.
   * </pre>
   */
  public static final class PositionServiceStub extends io.grpc.stub.AbstractAsyncStub<PositionServiceStub> {
    private PositionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PositionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PositionServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sendPosition(object_position.PositionServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<object_position.PositionServiceOuterClass.Position> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendPositionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The service definition.
   * </pre>
   */
  public static final class PositionServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<PositionServiceBlockingStub> {
    private PositionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PositionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PositionServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public object_position.PositionServiceOuterClass.Position sendPosition(object_position.PositionServiceOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendPositionMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The service definition.
   * </pre>
   */
  public static final class PositionServiceFutureStub extends io.grpc.stub.AbstractFutureStub<PositionServiceFutureStub> {
    private PositionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PositionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PositionServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<object_position.PositionServiceOuterClass.Position> sendPosition(
        object_position.PositionServiceOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendPositionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_POSITION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PositionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PositionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_POSITION:
          serviceImpl.sendPosition((object_position.PositionServiceOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<object_position.PositionServiceOuterClass.Position>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PositionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PositionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return object_position.PositionServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PositionService");
    }
  }

  private static final class PositionServiceFileDescriptorSupplier
      extends PositionServiceBaseDescriptorSupplier {
    PositionServiceFileDescriptorSupplier() {}
  }

  private static final class PositionServiceMethodDescriptorSupplier
      extends PositionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PositionServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PositionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PositionServiceFileDescriptorSupplier())
              .addMethod(getSendPositionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
