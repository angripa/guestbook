package com.guestbook.domain.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@Table(name = "visit")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Visit extends BaseEntity {

   private static final long serialVersionUID = -1917625953151684553L;

   @Column(name = "building_id", nullable = false,columnDefinition = "varchar(12) default ''")
   String buildingId = "";

   @Column(name = "username", nullable = false,columnDefinition = "varchar(128) default ''")
   String username = "";

   @Column(name = "token", nullable = false)
   String token = "";

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "checkin_time",nullable = false)
   Date checkinTime;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "checkout_time")
   Date checkoutTime;
}
