package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ProblemSourceCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSourceCodeRepo extends JpaRepository<ProblemSourceCodeEntity, String> {
}
