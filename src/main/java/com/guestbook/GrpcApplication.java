package com.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableCaching
//@EnableRetry
@EnableScheduling
@EnableJms
public class GrpcApplication {

   @PostConstruct
   public void init(){
      TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));   // It will set UTC timezone
   }
   public static void main(String[] args) {
      SpringApplication.run(GrpcApplication.class, args);
   }
}
