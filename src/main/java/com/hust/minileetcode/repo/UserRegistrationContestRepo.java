package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestEntity;
import com.hust.minileetcode.entity.UserRegistrationContestEntity;
import com.hust.minileetcode.rest.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRegistrationContestRepo extends JpaRepository<UserRegistrationContestEntity, UUID> {
    UserRegistrationContestEntity findUserRegistrationContestByContestAndUserLogin(ContestEntity contest, UserLogin userLogin);
}
