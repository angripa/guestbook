package com.guestbook.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class GrpcUtil<T extends MessageOrBuilder> {

   @Autowired
   ObjectMapper objectMapper;

   public String toJson(Object o) {
      try {
         return objectMapper.writeValueAsString(o);
      } catch (Exception e) {
         e.printStackTrace();
         return "";
      }
   }

   public void composeBuilder(Object request, Message.Builder builder) {
      try {
         if (request instanceof String)
            JsonFormat.parser().ignoringUnknownFields().merge((String) request, builder);
         else
            JsonFormat.parser().ignoringUnknownFields().merge(toJson(request), builder);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
