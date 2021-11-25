package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.UserSubmissionContestResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSubmissionContestResultRepo extends JpaRepository<UserSubmissionContestResultEntity, UUID> {
}
