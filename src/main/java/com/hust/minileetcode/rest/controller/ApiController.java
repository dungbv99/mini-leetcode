package com.hust.minileetcode.rest.controller;

import com.hust.minileetcode.rest.entity.Person;
import com.hust.minileetcode.rest.entity.UserLogin;
import com.hust.minileetcode.rest.service.SecurityGroupService;
import com.hust.minileetcode.rest.service.ApplicationService;
import com.hust.minileetcode.rest.service.PersonService;
import com.hust.minileetcode.rest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApiController {

    private UserService userService;
    private PersonService personService;
    private ApplicationService applicationService;
    private SecurityGroupService securityGroupService;

    @GetMapping("/")
    public ResponseEntity<Map> home(@CurrentSecurityContext(expression = "authentication.name") String name, Principal principal) {
        log.info("SecurityContextHolder.getContext().getAuthentication() {}", SecurityContextHolder.getContext().getAuthentication());
        log.info("principal {}", principal);
        Map<String, String> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        response.put("user", name);
        headers.set("Access-Control-Expose-Headers", "X-Auth-Token");
        return ResponseEntity.ok().headers(headers).body(response);
    }

    @GetMapping("/my-account")
    public ResponseEntity<?> getAccount(Principal principal) {
        UserLogin userLogin = userService.findById(principal.getName());
        Person person = userLogin.getPerson();
//        Party party = userLogin.getParty();
//        Person person = personService.findByPartyId(party.getPartyId());
        Map<String, String> response = new HashMap<>();
        response.put("name", person.getFullName());
//        response.put("partyId", person.getPartyId().toString());
        response.put("user", principal.getName());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok().body(securityGroupService.getRoles());
    }

    @GetMapping("screen-security")
    public ResponseEntity<?> getScrSecurInfo(Principal principal) {
        return ResponseEntity.ok().body(applicationService.getScrSecurInfo(principal.getName()));
    }
}
