package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestCaseRepo extends JpaRepository<TestCase, UUID> {
    TestCase findTestCaseByTestCaseId(UUID uuid);
    List<TestCase> findAllByContestProblem(ContestProblem contestProblem);
}
