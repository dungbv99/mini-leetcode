package com.hust.minileetcode.rest.service;

import com.hust.minileetcode.rest.entity.Person;
import com.hust.minileetcode.rest.repo.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonServiceImpl implements PersonService {

    PersonRepo personRepo;

    @Override
    public Person findByPersonId(UUID partyId) {
        return personRepo.findPersonByPersonId(partyId);
    }
}
