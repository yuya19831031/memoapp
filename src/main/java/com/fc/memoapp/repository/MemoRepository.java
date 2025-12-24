package com.fc.memoapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fc.memoapp.entity.MemoEntity;

public interface MemoRepository extends JpaRepository<MemoEntity, Long> {
    Page<MemoEntity> findByTitleContaining(
            String title,
            Pageable pageable
    );
}
