package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ProblemEntity;
import com.hust.minileetcode.entity.TestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface TestCaseRepo extends JpaRepository<TestCaseEntity, UUID> {
    TestCaseEntity findTestCaseByTestCaseId(UUID uuid);
    List<TestCaseEntity> findAllByProblemId(String problemId);

//    @Query("delete from TestCaseEntity t where t.problemId = :problemId")
//    void deleteAllTestCasesByProblemId(@PathVariable("problemId") String problemId);

    void deleteAllByProblemId(String problemId);
}
