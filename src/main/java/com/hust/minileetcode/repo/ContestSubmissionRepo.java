package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContestSubmissionRepo extends JpaRepository<ContestSubmission, UUID> {
}
