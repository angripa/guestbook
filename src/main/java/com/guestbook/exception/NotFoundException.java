package com.guestbook.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class NotFoundException extends StatusRuntimeException {
   public NotFoundException(Status status) {
      super(status);
   }

}
