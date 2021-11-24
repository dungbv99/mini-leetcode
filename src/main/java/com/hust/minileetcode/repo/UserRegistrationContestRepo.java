package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.UserRegistrationContest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRegistrationContestRepo extends JpaRepository<UserRegistrationContest, UUID> {
}
