package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestSubmissionEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.SqlResultSetMapping;
import java.util.List;
import java.util.UUID;

public interface ContestSubmissionRepo extends JpaRepository<ContestSubmissionEntity, UUID> {
//    List<Integer> getListProblemSubmissionDistinctWithHighestScore(@Param("userLogin") UserLogin userLogin);

    @Query(value = "select user_submission_id as userId, sum(p) as point from ( select user_submission_id, problem_id , max(point) as p from contest_submission_new where user_submission_id in (select user_id from user_registration_contest_new where contest_id=:contest_id and status='SUCCESSFUL') and contest_id=:contest_id group by problem_id, user_submission_id) as cur group by user_submission_id",
            nativeQuery = true
    )
    List<Object[]> calculatorContest(@Param("contest_id") String contest_id);

}
