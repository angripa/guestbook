package com.guestbook.service;

import com.alfa.grpc.visit.*;
import com.guestbook.config.JwtTokenUtil;
import com.guestbook.domain.entity.Visit;
import com.guestbook.domain.repository.VisitRepository;
import com.guestbook.exception.CommonException;
import io.grpc.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VisitService {
   @Autowired
   VisitRepository visitRepository;

   @Autowired
   JwtTokenUtil jwtTokenUtil;

   public VisitResponse checkin(VisitRequest request){
      Visit vs = visitRepository.findByUsernameAndTokenIsNotNull(request.getUserId());
      if(vs != null){
         return VisitResponse.newBuilder()
                 .setToken(vs.getToken())
                 .build();
      }
      Visit visit = new Visit();
      String token = jwtTokenUtil.generateToken(request.getUserId());
      visit.setBuildingId(request.getBuildingId());
      visit.setCheckinTime(new Date());
      visit.setUsername(request.getUserId());
      visit.setToken(token);
      visitRepository.save(visit);
      return VisitResponse.newBuilder()
              .setToken(token)
              .build();
   }
   public void checkout(CheckoutRequest request){
      String username = jwtTokenUtil.getUsernameFromToken(request.getToken());
      Visit visit = visitRepository.findByUsernameAndCheckoutTimeIsNull(username).<CommonException>orElseThrow(() -> {
         throw new CommonException(Status.INVALID_ARGUMENT.withDescription("User not found"));
      });
      visit.setCheckoutTime(new Date());
      visit.setToken(null);
      visitRepository.save(visit);
   }

   public VisitList visitList(VisitListRequest request){
      visitRepository.findByToken(request.getToken()).<CommonException>orElseThrow(() -> {
         throw new CommonException(Status.UNAUTHENTICATED.withDescription("Invalid token"));
      });
      String username = jwtTokenUtil.getUsernameFromToken(request.getToken());
      List<com.alfa.grpc.visit.Visit> ls = new ArrayList<>();
      visitRepository.findByUsername(username).ifPresent(vs -> {
         vs.forEach(visit -> {
            ls.add(com.alfa.grpc.visit.Visit.newBuilder()
                    .setBuildingId(visit.getBuildingId())
                    .setId(visit.getId())
                    .setUserId(visit.getUsername())
                    .setCheckoutTime(getCheckoutTime(visit.getCheckoutTime()))
                    .setCheckinTime(visit.getCheckinTime().toString())
                    .build());
         });

      });
      return VisitList.newBuilder()
              .addAllVisit(ls)
              .build();
   }

   private String getCheckoutTime(Date date){
      if(date == null)
         return "";
      return String.valueOf(date);
   }
}
