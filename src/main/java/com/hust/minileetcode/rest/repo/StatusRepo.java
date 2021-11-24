package com.hust.minileetcode.rest.repo;

import com.hust.minileetcode.rest.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepo extends JpaRepository<Status, String> {
    Status findStatusById(String statusId);
}

