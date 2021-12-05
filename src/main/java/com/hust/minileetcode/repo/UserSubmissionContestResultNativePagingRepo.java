package com.hust.minileetcode.repo;

import com.hust.minileetcode.composite.UserSubmissionContestResultID;
import com.hust.minileetcode.entity.UserSubmissionContestResultNativeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserSubmissionContestResultNativePagingRepo extends PagingAndSortingRepository<UserSubmissionContestResultNativeEntity, UserSubmissionContestResultID> {
    Page<UserSubmissionContestResultNativeRepo> findAllByContestId(Pageable pageable, String contestId);
}
