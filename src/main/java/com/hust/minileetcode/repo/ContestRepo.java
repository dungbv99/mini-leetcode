package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestEntity;
import com.hust.minileetcode.rest.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepo extends JpaRepository<ContestEntity, String> {
    ContestEntity findContestByContestId(String contestId);

    ContestEntity findContestEntityByContestIdAndUserId(String contestId, String userId);

    void deleteByContestIdAndUserId(String contestId, String userId);
}
