package com.guestbook;

import com.alfa.grpc.visit.VisitListRequest;
import com.alfa.grpc.visit.VisitRequest;
import com.alfa.grpc.visit.VisitResponse;
import com.alfa.grpc.visit.VisitServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GrpcClient {
   final static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb2RlIjoiMTAwMCIsInJvbGUiOiJST0xFX0RFQklUVVIiLCJ1c2VyX25hbWUiOiIwODUzNjQ1Njg1NTIiLCJ1c2VyTm8iOjQ5MSwiZW1wbG95ZWVJZCI6LTYzLCJ1c2VyTmFtZSI6IjA4NTM2NDU2ODU1MiIsInN0b3JlSWQiOi02MywiZW1wbG95ZWUiOnRydWUsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0RFQklUVVIifV0sImNsaWVudF9pZCI6IkJhbmdrdS1JRCIsImFjY291bnRJZCI6LTYzLCJtZW51Um9sZUxpc3QiOltdLCJ1c2VyTW9iaWxlIjoiMDg1MzY0NTY4NTUyIiwic2NvcGUiOlsidHJ1c3QiLCJyZWFkIiwid3JpdGUiXSwidXNlckVtYWlsIjpudWxsLCJleHAiOjE1ODExNDU2NjMsImFwcFNvdXJjZSI6Ik4vQSIsImp0aSI6IjNiNzE1NzU5LTgxZDItNDRhOS05MDI1LWM3YWVhYjI2NzhiYyIsImFjY291bnQiOmZhbHNlLCJ0aW1lbGFwc2UiOjE1NzI1OTIwNjMyODR9.GYJjJ7KQrV4zW3I4lWxdwMNLYV4ft9vkEpQwNN5vkDg";

   public static void main(String[] args) {
//      ManagedChannel channel = NettyChannelBuilder.forAddress("172.31.212.143", 7806)
      ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 7806)
              .usePlaintext()
              .build();

      VisitResponse visitResponse = VisitServiceGrpc.newBlockingStub(channel).add(VisitRequest.newBuilder()
              .setBuildingId("B0001")
              .setUserId("test@gmail.com")
              .build());
      log.info(visitResponse.getToken());

//         PasswordServiceGrpc.newBlockingStub(channel).withCallCredentials(new AuthCallCredential(token)).changePassword(ChangePasswordRequest.newBuilder()
//                 .setUserId("082768514189")
//                 .build());
//      }

      channel.shutdown();
   }
}
