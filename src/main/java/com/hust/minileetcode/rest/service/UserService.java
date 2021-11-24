package com.hust.minileetcode.rest.service;

import com.hust.minileetcode.rest.entity.UserLogin;
import com.hust.minileetcode.rest.model.*;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserLogin findById(String userLoginId);

    @Transactional
    SimpleResponse register(RegisterIM im);

//    DPerson findByPartyId(String partyId);

//    Page<DPerson> findAllPerson(Pageable page, SortAndFiltersInput query);
//
//    Page<UserRestBriefProjection> findPersonByFullName(Pageable page, String sString);
//
//    Page<UserRestBriefProjection> findUsersByUserLoginId(Pageable page, String sString);
//
//    List<UserLogin> getAllUserLogins();
//
    UserLogin createAndSaveUserLogin(String userName, String password);
//
//    UserLogin updatePassword(UserLogin user, String password);
//
    void createAndSaveUserLogin(PersonModel personModel) throws Exception;
//
//    Party update(PersonUpdateModel personUpdateModel, UUID partyId);
//
//    UserLogin findUserLoginByPartyId(UUID partyId);
//
//    SimpleResponse register(RegisterIM im);
//
    GetAllRegistersOM getAllRegisters();

    SimpleResponse approve(ApproveRegistrationIM im);
//
    SimpleResponse disableUserRegistration(DisableUserRegistrationIM im);
//
//    UserLogin updatePassword2(String userLoginId, String password);
//
//    List<UserLogin> getALlUserLoginsByGroupId(String groupId);
//
//    List<String> getGroupPermsByUserLoginId(String userLoginId);
//
//    PersonModel findPersonByUserLoginId(String userLoginId);
//
//    List<String> findAllUserLoginIdOfGroup(String groupId);
}
