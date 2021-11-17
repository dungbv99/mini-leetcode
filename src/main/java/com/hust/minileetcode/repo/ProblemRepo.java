package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ProblemRepo extends JpaRepository<Problem, String> {
    Problem findByProblemId(String problemId);

    @Query("select p.problemName from Problem p")
    ArrayList<String> findAllProblemName();


}
