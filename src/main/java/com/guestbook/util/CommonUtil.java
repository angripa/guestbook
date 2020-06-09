package com.guestbook.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Component
public class CommonUtil {
   @Value("${app.kyc.dir:${project.home}/kyc}")
   String kycDir;

   @PostConstruct
   void initKycDir() {
      File f = new File(kycDir);
      if (!f.exists()) {
         f.mkdir();
      }
   }

   public Resource downloadFromUrl(String url, String filename) {
      String filePath = kycDir.concat("/").concat(filename);
      try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

           FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
         byte dataBuffer[] = new byte[1024];
         int bytesRead;
         while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
         }
         File file = new File(filePath);
         return new FileSystemResource(file);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

//   public byte[] downloadFromUrl(String uri) throws IOException {
//
//      try {
//         URL url = new URL(uri);
//         URLConnection conn = url.openConnection();
//         conn.setConnectTimeout(5000);
//         conn.setReadTimeout(5000);
//         conn.connect();
//
//         ByteArrayOutputStream baos = new ByteArrayOutputStream();
//         IOUtils.copy(conn.getInputStream(), baos);
//
//         return baos.toByteArray();
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
//      return null;
//   }
}
