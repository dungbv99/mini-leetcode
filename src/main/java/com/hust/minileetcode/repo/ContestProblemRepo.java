package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestProblem;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface ContestProblemRepo extends JpaRepository<ContestProblem, String> {
    ContestProblem findByProblemId(String problemId);

    @Query("select cp.problemName from ContestProblem cp")
    ArrayList<String> findAllProblemName();
}
