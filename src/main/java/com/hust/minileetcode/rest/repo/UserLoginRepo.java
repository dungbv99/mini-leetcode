package com.hust.minileetcode.rest.repo;


import com.hust.minileetcode.rest.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface UserLoginRepo extends JpaRepository<UserLogin, String> {
    UserLogin findByUserLoginId(String userLoginId);

}

