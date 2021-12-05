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

    @Query(value = "select user_submission_id as userId, sum(p) as point, email, first_name, middle_name, last_name from " +
            "( select user_submission_id, problem_id , max(point) as p, ul.email as email, person.first_name as first_name, person.middle_name as middle_name, person.last_name as last_name " +
            "from contest_submission_new csn " +
            "inner join user_login ul " +
            "on user_submission_id in (select user_id from user_registration_contest_new urcn where urcn.contest_id=:contest_id and status='SUCCESSFUL') " +
            "and csn.contest_id=:contest_id " +
            "and csn.user_submission_id = ul.user_login_id " +
            "inner join person  " +
            "on person.person_id = ul.person_id " +
            "group by problem_id, user_submission_id, ul.email, problem_id, user_submission_id, person.first_name, person.middle_name, person.last_name) " +
            "as cur group by user_submission_id, email, first_name, middle_name, last_name order by point desc "
            ,
            nativeQuery = true
    )
    List<Object[]> calculatorContest(@Param("contest_id") String contest_id);

}
