package com.hust.minileetcode.rest.service;

import com.hust.minileetcode.rest.entity.SecurityGroup;
import com.hust.minileetcode.rest.model.GetAllRolesOM;

import java.util.List;
import java.util.Set;

public interface SecurityGroupService {

    List<SecurityGroup> findAll();

    Set<GetAllRolesOM> getRoles();
}