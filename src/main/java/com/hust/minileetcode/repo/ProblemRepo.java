package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ProblemRepo extends JpaRepository<ProblemEntity, String> {
    ProblemEntity findByProblemId(String problemId);

    @Query("select p.problemName from ProblemEntity p")
    ArrayList<String> findAllProblemName();


}
