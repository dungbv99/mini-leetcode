package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestProblemRepo extends JpaRepository<ContestProblem, String> {
    ContestProblem findByProblemId(String problemId);


}
