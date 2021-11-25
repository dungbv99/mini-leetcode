package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.UserSubmissionContestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSubmissionContestResultRepo extends JpaRepository<UserSubmissionContestResult, UUID> {
}
