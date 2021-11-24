package com.hust.minileetcode.rest.repo;

import com.hust.minileetcode.rest.entity.StatusItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusItemRepo extends JpaRepository<StatusItem, String> {

    StatusItem findByStatusId(String statusId);

    List<StatusItem> findAllByStatusIdStartsWith(String statusId);
}
