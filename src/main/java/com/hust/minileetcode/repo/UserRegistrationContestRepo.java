package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.Contest;
import com.hust.minileetcode.entity.UserRegistrationContest;
import com.hust.minileetcode.rest.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRegistrationContestRepo extends JpaRepository<UserRegistrationContest, UUID> {
    UserRegistrationContest findUserRegistrationContestByContestAndUserLogin(Contest contest, UserLogin userLogin);
}
