package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ProblemEntity;
import com.hust.minileetcode.entity.TestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestCaseRepo extends JpaRepository<TestCaseEntity, UUID> {
    TestCaseEntity findTestCaseByTestCaseId(UUID uuid);
    List<TestCaseEntity> findAllByProblem(ProblemEntity problem);
}
