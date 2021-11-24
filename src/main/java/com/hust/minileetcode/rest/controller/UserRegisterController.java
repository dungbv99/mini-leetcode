package com.hust.minileetcode.rest.controller;

import com.hust.minileetcode.rest.model.ApproveRegistrationIM;
import com.hust.minileetcode.rest.model.DisableUserRegistrationIM;
import com.hust.minileetcode.rest.model.RegisterIM;
import com.hust.minileetcode.rest.model.SimpleResponse;
import com.hust.minileetcode.rest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@Slf4j
public class UserRegisterController {
    private UserService userService;

//    @Autowired
//    private RegisteredAffiliationService registeredAffiliationService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterIM im) {
        log.info("register im {}", im.toString());
        SimpleResponse res = userService.register(im);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @GetMapping("/user/registration-list")
    public ResponseEntity<?> getAllRegists() {
        return ResponseEntity.ok().body(userService.getAllRegisters());
    }

    @PostMapping("/user/approve-registration")
        public ResponseEntity<?> approve(@RequestBody ApproveRegistrationIM im) {
        SimpleResponse res = userService.approve(im);
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    @PostMapping("/user/disable-registration")
    public ResponseEntity<?> disableUserRegistration(Principal principal, @RequestBody DisableUserRegistrationIM input){
        SimpleResponse res = userService.disableUserRegistration(input);
        return ResponseEntity.status(res.getStatus()).body(res);
    }
}
