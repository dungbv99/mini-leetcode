package com.hust.minileetcode.rest.service;

import com.hust.minileetcode.rest.entity.UserLogin;
import com.hust.minileetcode.rest.repo.UserLoginRepo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
@javax.transaction.Transactional
public class UserServiceImpl implements UserService {
    private final UserLoginRepo userLoginRepo;
    @Override
    public UserLogin findById(String userLoginId) {
        return userLoginRepo.findByUserLoginId(userLoginId);

    }
}
