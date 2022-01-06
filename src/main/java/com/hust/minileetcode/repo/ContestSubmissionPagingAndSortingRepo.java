package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestSubmissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ContestSubmissionPagingAndSortingRepo extends PagingAndSortingRepository<ContestSubmissionEntity, UUID> {
    Page<ContestSubmissionEntity> findAllByContestId(Pageable pageable, String contestId);
}
