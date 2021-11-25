package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContestPagingAndSortingRepo extends PagingAndSortingRepository<ContestEntity, String> {
    Page<ContestEntity> findAll(Pageable pageable);

}
