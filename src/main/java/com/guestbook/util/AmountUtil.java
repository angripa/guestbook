package com.guestbook.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class AmountUtil {
   static final DecimalFormat df = new DecimalFormat("Rp #,###.00");

   public static String parse(Object o) {
      if (o instanceof BigDecimal)
         return df.format(o);
      else if (o instanceof BigInteger)
         return df.format(o);
      else if(o instanceof Integer)
         return df.format(o);
      else return "";
   }
}
