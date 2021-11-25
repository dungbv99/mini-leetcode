package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepo extends JpaRepository<ContestEntity, String> {
    ContestEntity findContestByContestId(String contestId);
}
