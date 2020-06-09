package com.guestbook.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class ParamException extends StatusRuntimeException {

   public ParamException(Status status) {
      super(status);
   }
}
