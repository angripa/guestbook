package com.guestbook.util;

public class LoanUtil {
   public static String convertLoanStatus(String st) {
      String status = "";
      switch (st) {
         case "RQ":
            status = "Dalam Proses";
            break;
         case "VA":
         case "SA":
         case "BA":
            status = "Diterima";
            break;
         case "VD":
         case "SD":
         case "BD":
            status = "Ditolak";
            break;
         case "DN":
            status = "Selesai";
            break;
      }
      return status;
   }

   public static String calculateLoanDuration(int duration) {
      if (duration <= 12)
         return String.format("%d bulan", duration);
      else {
         int year = duration / 12;
         int month = duration % 12;
         if (month == 0)
            return String.format("%d tahun", year);
         else
            return String.format("%d tahun %d bulan", year, month);
      }

   }
}
