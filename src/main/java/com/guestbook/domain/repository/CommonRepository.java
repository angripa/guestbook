package com.guestbook.domain.repository;

import com.guestbook.domain.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CommonRepository<T extends BaseEntity> {
   Page<T> findByIdAndDeletedByIsNull(String id, Pageable pageable);

   Optional<T> findByIdAndDeletedByIsNull(String id);

   Page<T> findAllByDeletedByIsNull(Pageable pageable);

   Optional<List<T>> findByDeletedByIsNull();

   Optional<List<T>> findByDeletedByIsNull(Pageable pageable);
}
