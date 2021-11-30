package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.UserSubmissionContestResultNativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSubmissionContestResultNativeRepo extends JpaRepository<UserSubmissionContestResultNativeEntity, UUID> {
}
