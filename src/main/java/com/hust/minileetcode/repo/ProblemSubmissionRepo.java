package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ProblemEntity;
import com.hust.minileetcode.entity.ProblemSubmissionEntity;
import com.hust.minileetcode.rest.entity.UserLogin;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProblemSubmissionRepo extends JpaRepository<ProblemSubmissionEntity, UUID> {
    @Query("select p.problemSubmissionId, p.timeSubmitted, p.status, p.score, p.runtime, p.memoryUsage, p.sourceCodeLanguages from ProblemSubmissionEntity p where p.userLogin = :user and p.problem = :problem")
    List<Object[]> getListProblemSubmissionByUserAndProblemId(@Param("user")UserLogin user, @Param("problem") ProblemEntity problem);

    ProblemSubmissionEntity findByProblemSubmissionId(UUID id);

//    @Query(value = "select  from ProblemSubmissionEntity ", nativeQuery = true)
//    List<Object[]> test();

}
