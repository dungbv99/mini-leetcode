package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestEntity;
import com.hust.minileetcode.entity.UserRegistrationContestEntity;
import com.hust.minileetcode.model.ModelUserRegisteredClassInfo;
import com.hust.minileetcode.rest.entity.UserLogin;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface UserRegistrationContestPagingAndSortingRepo extends PagingAndSortingRepository<UserRegistrationContestEntity, UUID> {
    @Query("select new com.hust.minileetcode.model.ModelUserRegisteredClassInfo(ul.email, ul.userLoginId, p.middleName, p.firstName, p.lastName) from UserRegistrationContestEntity urce " +
            "inner join ContestEntity ce on urce.status =:status and urce.contest = :contest and urce.contest = ce " +
            "inner join UserLogin ul on urce.userLogin = ul " +
            "inner join Person p on ul.person = p")
    Page<ModelUserRegisteredClassInfo> getAllUserRegisteredByContestAndStatusInfo(Pageable pageable, @Param("contest") ContestEntity contest, @Param("status") String status);

    List<UserRegistrationContestEntity> findUserRegistrationContestEntityByContest(Pageable pageable,ContestEntity contest);

    List<UserRegistrationContestEntity> findUserRegistrationContestEntityByContestAndStatus(Pageable pageable, ContestEntity contest, String status);

    @Query("select ce from ContestEntity ce where ce in (select urce.contest from UserRegistrationContestEntity urce where urce.userLogin = :userLogin and urce.status = 'SUCCESSFUL')")
    Page<ContestEntity> getContestByUserAndStatusSuccessful(Pageable pageable, @Param("userLogin") UserLogin userLogin);

    @Query("select ce from ContestEntity ce where ce not in (select urce.contest from UserRegistrationContestEntity urce where urce.userLogin = :userLogin and urce.status = 'SUCCESSFUL')")
    Page<ContestEntity> getNotRegisteredContestByUserLogin(Pageable pageable, @Param("userLogin") UserLogin userLogin);
}
