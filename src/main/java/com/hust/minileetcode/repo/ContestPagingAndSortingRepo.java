package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.Contest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ContestPagingAndSortingRepo extends PagingAndSortingRepository<Contest, String> {
    Page<Contest> findAll(Pageable pageable);

}
