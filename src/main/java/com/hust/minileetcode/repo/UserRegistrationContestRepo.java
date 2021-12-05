package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestEntity;
import com.hust.minileetcode.entity.UserRegistrationContestEntity;
import com.hust.minileetcode.model.ModelUserRegisteredClassInfo;
import com.hust.minileetcode.rest.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRegistrationContestRepo extends JpaRepository<UserRegistrationContestEntity, UUID> {
    UserRegistrationContestEntity findUserRegistrationContestByContestAndUserLogin(ContestEntity contest, UserLogin userLogin);

    UserRegistrationContestEntity findUserRegistrationContestEntityByContestAndUserLoginAndStatus(ContestEntity contest, UserLogin userLogin, String status);
//    List<ModelUserRegisteredClassInfo> getAllUserRegisteredContestInfo()
}
