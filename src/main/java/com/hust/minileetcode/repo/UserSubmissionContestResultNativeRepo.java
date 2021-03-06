package com.hust.minileetcode.repo;

import com.hust.minileetcode.composite.UserSubmissionContestResultID;
import com.hust.minileetcode.entity.UserSubmissionContestResultNativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSubmissionContestResultNativeRepo extends JpaRepository<UserSubmissionContestResultNativeEntity, UserSubmissionContestResultID> {

    void deleteAllByContestId(String contestId);
}
