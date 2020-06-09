
package com.guestbook.util;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author angripa.nadapdap
 * @version $Id: AccountUtil.java, v 0.1 2019‐03‐30 10:27 angripa.nadapdap Exp $$
 */
public class AccountUtil {

   private static final String PHONE_NUMBER_PATTERN = "^\\+?(62)?-?0*(\\d{9,12})$";

   public static String formatUserId(String uid) {
      String localNumber = "0".concat(getLocalNumber(uid));
      return localNumber.replace("-", "");
   }

   public static boolean isIndonesiaPhoneNumber(String rawPhoneNumber) {
      return !getLocalNumber(rawPhoneNumber).isEmpty();
   }

   public static String getLocalNumber(String rawPhoneNumber) {
      Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
      String removeDash = rawPhoneNumber.replace("-", "");
      Matcher matcher = pattern.matcher(removeDash);

      if (matcher.find()) {
         String localPhoneNumber = matcher.group(2);
         return localPhoneNumber;
      }
      return "";
   }

   public static String parseAccountName(String name) {
      String str[] = name.split(" ");

      return str.length == 1 ? str[0] : str[0].concat(" ").concat(str[str.length - 1]);
   }

   public static String getFamilyName(String name) {
      String str[] = name.split(" ");

      return str.length == 1 ? str[0] : str[str.length - 1];
   }

   public static String getFirstName(String name) {
      String str[] = name.split(" ");

      return str[0];
   }

   public static String getUserLogin() {
      if (SecurityContextHolder.getContext().getAuthentication() == null)
         return "system";
      return SecurityContextHolder.getContext().getAuthentication().getName();
   }
}
