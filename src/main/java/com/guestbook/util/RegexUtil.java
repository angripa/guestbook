package com.guestbook.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
   public static boolean regexValidate(String value, String regex) {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(value);

      return matcher.matches();
   }
}
