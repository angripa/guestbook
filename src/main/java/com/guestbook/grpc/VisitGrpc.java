package com.guestbook.grpc;

import com.alfa.grpc.visit.CheckoutRequest;
import com.alfa.grpc.visit.VisitList;
import com.alfa.grpc.visit.VisitListRequest;
import com.alfa.grpc.visit.VisitServiceGrpc;
import com.google.protobuf.Empty;
import com.guestbook.service.VisitService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@GRpcService
public class VisitGrpc extends VisitServiceGrpc.VisitServiceImplBase {
   @Autowired
   VisitService visitService;

   @Override
   public void add(com.alfa.grpc.visit.VisitRequest request, StreamObserver<com.alfa.grpc.visit.VisitResponse> responseObserver) {
      log.info("VisitGrpc.add() : {}",request);
      responseObserver.onNext(visitService.checkin(request));
      responseObserver.onCompleted();
   }

   @Override
   public void list(VisitListRequest request, StreamObserver<VisitList> responseObserver) {
      responseObserver.onNext(visitService.visitList(request));
      responseObserver.onCompleted();
   }

   @Override
   public void checkout(CheckoutRequest request, StreamObserver<Empty> responseObserver) {
      visitService.checkout(request);
      responseObserver.onNext(Empty.newBuilder().build());
      responseObserver.onCompleted();
   }
}
