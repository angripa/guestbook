package com.guestbook.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class RandomUtil {

   public String randNumeric(int n) {
      return RandomStringUtils.randomNumeric(n);
   }

}
