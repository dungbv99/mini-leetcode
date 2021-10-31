package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepo extends JpaRepository<Contest, String> {
    Contest findContestByContestId(String contestId);
}
