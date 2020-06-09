package com.guestbook.util;

import com.guestbook.constant.ResultCode;
import com.guestbook.exception.CommonException;
import io.grpc.Status;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Log4j2
public class DateUtil {

   public static final SimpleDateFormat simpleDateFormatDefault = new SimpleDateFormat("dd-MM-yyyy");
   public static final SimpleDateFormat simpleDateTimeFormatDefault = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

   public static Date addMonths(@Nonnull Integer month) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.MONTH, month);
      return calendar.getTime();
   }

   public static Date addDays(@Nonnull Integer days) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DATE, days);
      return calendar.getTime();
   }

   public static Date addHours(@Nonnull Integer hours) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.HOUR, hours);
      return calendar.getTime();
   }

   public static String parseToDefaultDateFormat(Date date) {
      try {
         return simpleDateFormatDefault.format(date);
      }catch (Exception e){
         log.error("Invalid date "+date);
         throw new CommonException(Status.INVALID_ARGUMENT.withDescription(ResultCode.INVALID_PARAM_DATE.getMessage()));
      }
   }

   public static String parseToDefaultDateTimeFormat(Date date) {

      try {
         return simpleDateTimeFormatDefault.format(date);
      }catch (Exception e){
         log.error("Invalid date "+date);
         throw new CommonException(Status.INVALID_ARGUMENT.withDescription(ResultCode.INVALID_PARAM_DATE.getMessage()));
      }
   }

   public static String parseToDateFormat(Date date, String format) {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(date);
   }

   public static Long convertToTimestamp(Date date){
      Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
      instance.setTime(date);
      return instance.getTimeInMillis();
   }
   public static Date convertToDefaultDateTimeFormat(String date) {
      try {
         return simpleDateTimeFormatDefault.parse(date);
      }catch (Exception e){
         log.error("Invalid date "+date);
         throw new CommonException(Status.INVALID_ARGUMENT.withDescription(ResultCode.INVALID_PARAM_DATE.getMessage()));
      }

   }

   public static Date convertToDefaultDateFormat(String date) {
      try {
         return simpleDateFormatDefault.parse(date);
      }catch (Exception e){
         log.error("Invalid date "+date);
         throw new CommonException(Status.INVALID_ARGUMENT.withDescription(ResultCode.INVALID_PARAM_DATE.getMessage()));
      }

   }

}
