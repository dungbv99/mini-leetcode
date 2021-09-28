package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ProblemSourceCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProblemSourceCodeRepo extends JpaRepository<ProblemSourceCode, String> {
}
