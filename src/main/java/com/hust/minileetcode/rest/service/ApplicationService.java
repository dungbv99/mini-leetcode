package com.hust.minileetcode.rest.service;

import com.hust.minileetcode.rest.entity.Application;
import com.hust.minileetcode.rest.entity.SecurityPermission;

import java.util.List;

public interface ApplicationService {

    List<Application> getListByPermissionAndType(List<SecurityPermission> permissionList, String type);

    Application getById(String applicationId);

    List<String> getScrSecurInfo(String userId);
}

