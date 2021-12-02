package com.hust.minileetcode.rest.model;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonModel {

    private String userName;

    private String password;

    private List<String> roles;

    private String partyCode;

    private String firstName;

    private String lastName;

    private String middleName;

    private String gender;

    private Date birthDate;

    private String affiliations;

    private String email;
}
