package com.guestbook.config.interceptor;

import com.guestbook.exception.CommonException;
import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GlobalInterceptorConfiguration {

   Logger logger = LoggerFactory.getLogger(GlobalInterceptorConfiguration.class);

   @Bean
   @GRpcGlobalInterceptor
   public ServerInterceptor globalInterceptor() {
      return new ServerInterceptor() {
         @Override
         public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata metadata, ServerCallHandler<ReqT, RespT> next) {
            // your logic here
            logger.info("Incoming request {} ", call.getMethodDescriptor().getFullMethodName());
            String authorizationHeader = metadata.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
            if (null == authorizationHeader || "".equals(authorizationHeader) || "system".equals(authorizationHeader)) {
               logger.info("authorizationHeader {}",authorizationHeader);
            }else {
               String token = authorizationHeader.replace("Bearer ","");
//               OAuth2Authentication oAuth2Authentication = remoteTokenServices.loadAuthentication(token);
//               SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
            }
            ServerCall.Listener<ReqT> listener = next.startCall(call, metadata);
            return new ExceptionHandlingServerCallListener<>(listener, call, metadata);
         }
      };
   }

   private class ExceptionHandlingServerCallListener<ReqT, RespT>
           extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {
      private ServerCall<ReqT, RespT> serverCall;
      private Metadata metadata;

      ExceptionHandlingServerCallListener(ServerCall.Listener<ReqT> listener, ServerCall<ReqT, RespT> serverCall,
                                          Metadata metadata) {
         super(listener);
         this.serverCall = serverCall;
         this.metadata = metadata;
      }

      @Override
      public void onHalfClose() {
         try {
            super.onHalfClose();
         } catch (RuntimeException ex) {
            handleException(ex, serverCall, metadata);
            throw ex;
         }
      }

      @Override
      public void onReady() {
         try {
            super.onReady();
         } catch (RuntimeException ex) {
            handleException(ex, serverCall, metadata);
            throw ex;
         }
      }

      private void handleException(RuntimeException exception, ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
         if (exception instanceof CommonException) {
            serverCall.close(((CommonException) exception).getStatus(), metadata);
         } else if (exception instanceof IllegalArgumentException) {
            serverCall.close(Status.INVALID_ARGUMENT.withDescription(exception.getMessage()), metadata);
         } else {
            exception.printStackTrace();
            serverCall.close(Status.UNKNOWN.withDescription("system.busy"), metadata);
         }
      }
   }

}
