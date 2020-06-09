package com.guestbook.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class CommonException extends StatusRuntimeException {
   public CommonException(Status status) {
      super(status);
   }
}
