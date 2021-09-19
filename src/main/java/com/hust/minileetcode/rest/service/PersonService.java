package com.hust.minileetcode.rest.service;

import com.hust.minileetcode.rest.entity.Person;

import java.util.UUID;

public interface PersonService {

    Person findByPartyId(UUID partyId);
}
