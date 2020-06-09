package com.guestbook.domain.repository;

import com.guestbook.domain.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, String>, CommonRepository<Visit> {
   Optional<List<Visit>> findByUsername(String username);
   Optional<Visit> findByUsernameAndCheckoutTimeIsNull(String username);
   Visit findByUsernameAndTokenIsNotNull(String username);
   Optional<Visit> findByToken(String token);
}
