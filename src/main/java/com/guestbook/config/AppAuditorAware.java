package com.guestbook.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppAuditorAware implements AuditorAware<String> {
   @Override
   public Optional<String> getCurrentAuditor() {
      Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
      return Optional.of(authentication.isPresent()?authentication.get().getName():"system");
   }
}
