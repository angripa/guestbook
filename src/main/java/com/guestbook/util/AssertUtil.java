package com.guestbook.util;

import com.guestbook.exception.CommonException;
import io.grpc.Status;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Component
public class AssertUtil implements Serializable {

   private static final long serialVersionUID = 4174197081114404861L;

   public void isFalse(Boolean b, Status status) {
      isTrue(!b, status);
   }

   public void isTrue(Boolean b, Status status) {
      if (b)
         throw new CommonException(status);
   }

   public void isNull(Object o, Status status) {
      if (Objects.isNull(o))
         throw new CommonException(status);
   }

   public void notNull(Object o, Status status) {
      if (!Objects.isNull(o))
         throw new CommonException(status);
   }

   public void isEmpty(String str, Status status) {
      if (str == null || str.isEmpty())
         throw new CommonException(status);
   }

   public void regexNotValid(String str, String regex, Status status) {
      if (str == null || str.isEmpty() || !RegexUtil.regexValidate(str, regex))
         throw new CommonException(status);
   }

   public void isEqual(Object o, Object ob, Status status) {
      if (Objects.equals(o, ob))
         throw new CommonException(status);
   }

   public void notEqual(Object o, Object ob, Status status) {
      if (!Objects.equals(o, ob))
         throw new CommonException(status);
   }

   public void isEmptyList(List list, Status status) {
      if (list == null || list.isEmpty())
         throw new CommonException(status);
   }

   public void notEmptyList(List list, Status status) {
      if (list != null && !list.isEmpty())
         throw new CommonException(status);
   }

   public void notNumeric(String o, Status status) {
      isFalse(NumberUtils.isDigits((String) o), status);
   }
   public void numeric(String o, Status status) {
      isTrue(NumberUtils.isDigits((String) o), status);
   }
}
