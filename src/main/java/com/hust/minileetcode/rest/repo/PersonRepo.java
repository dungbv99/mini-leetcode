package com.hust.minileetcode.rest.repo;

import com.hust.minileetcode.rest.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PersonRepo extends JpaRepository<Person, UUID> {

    Person findPersonByPersonId(UUID personId);
}
