package com.guestbook.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BaseEntity implements Serializable {
   @GeneratedValue(generator = "uuid")
   @GenericGenerator(name = "uuid", strategy = "uuid2")
   @Column(name = "id",length = 36)
   @Id
   private String id;

   @CreatedBy
   @Column(name = "created_by",updatable = false,columnDefinition = "varchar(36) default 'system'")
   private String createdBy;

   @CreatedDate
   @Column(name = "date_created")
   @Temporal(TemporalType.TIMESTAMP)
   private Date dateCreated;

   @LastModifiedBy
   @Column(name = "modified_by")
   private String modifiedBy;

   @LastModifiedDate
   @Column(name = "date_modified")
   @Temporal(TemporalType.TIMESTAMP)
   private Date dateModified;

   @Column(name = "deleted_by")
   private String deletedBy;

   @Column(name = "date_deleted")
   @Temporal(TemporalType.TIMESTAMP)
   private Date dateDeleted;
}
