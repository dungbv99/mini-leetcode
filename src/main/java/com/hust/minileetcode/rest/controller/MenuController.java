package com.hust.minileetcode.rest.controller;

import com.hust.minileetcode.rest.constant.ApplicationTypeConstant;
import com.hust.minileetcode.rest.entity.Application;
import com.hust.minileetcode.rest.entity.SecurityGroup;
import com.hust.minileetcode.rest.entity.SecurityPermission;
import com.hust.minileetcode.rest.entity.UserLogin;
import com.hust.minileetcode.rest.service.ApplicationService;
import com.hust.minileetcode.rest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MenuController {

    public static final String module = MenuController.class.getName();
    private ApplicationService applicationService;
    private UserService userService;

    @GetMapping("/menu")
    public ResponseEntity<Set> getMenu(Principal principal) {
        UserLogin userLogin = userService.findById(principal.getName());
//        System.out.println(module + "::getMenu, userName = " + principal.getName());

        List<SecurityPermission> permissionList = new ArrayList<>();
        for (SecurityGroup securityGroup : userLogin.getRoles()) {
            permissionList.addAll(securityGroup.getPermissions());
        }
//        System.out.println(module + "::getMenu, userName = " + principal.getName() + ", meu.lst = " + permissionList.size());
        return ResponseEntity.ok().body(
                applicationService
                        .getListByPermissionAndType(permissionList, ApplicationTypeConstant.MENU)
                        .stream()
                        .map(Application::getApplicationId)
                        .collect(Collectors.toSet())
        );
    }
}
