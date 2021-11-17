package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.UserSubmissionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSubmissionResultRepo extends JpaRepository<UserSubmissionResult, UUID> {
}
