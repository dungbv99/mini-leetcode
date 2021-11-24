package com.hust.minileetcode.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRegistersOM {

    List<RegistsOM> regists = new ArrayList<>();

    Set<GetAllRolesOM> roles;
}