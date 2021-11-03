package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.ProblemSubmission;
import com.hust.minileetcode.rest.entity.UserLogin;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProblemSubmissionRepo extends JpaRepository<ProblemSubmission, UUID> {
    @Query("select p.problemSubmissionId, p.timeSubmitted, p.status, p.score, p.runtime, p.memoryUsage, p.sourceCodeLanguages from ProblemSubmission p where p.userLogin = :user and p.contestProblem = :problem")
    List<Object[]> getListProblemSubmissionByUserAndProblemId(@Param("user")UserLogin user, @Param("problem")ContestProblem problem);

    ProblemSubmission findByProblemSubmissionId(UUID id);
}
