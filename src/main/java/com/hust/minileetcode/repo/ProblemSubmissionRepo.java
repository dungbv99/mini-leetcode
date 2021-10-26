package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ProblemSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProblemSubmissionRepo extends JpaRepository<ProblemSubmission, UUID> {
}
