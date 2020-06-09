package com.guestbook.util;

import com.guestbook.exception.ParamException;
import io.grpc.Status;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class ParamsUtil implements Serializable {

   private static final long serialVersionUID = 4174197081114404861L;

   public void check(Object o) {
      check(o, Status.UNKNOWN);
   }

   public void check(Object o, Status status) {
      if (Objects.isNull(o))
         throw new ParamException(status);
   }

   public void check(Object o, String name, Status status) {
      if (Objects.isNull(o))
         throw new ParamException(status);
   }

   public void isEmpty(String str) {
      check(str, Status.UNKNOWN);
   }

   public void isEmpty(String str, Status status) {
      isEmpty(str, "undefined", status);
   }

   public void isEmpty(String str, String name, Status status) {
      if (str == null || str.isEmpty()) {

         throw new ParamException(status);
      }
   }
}
