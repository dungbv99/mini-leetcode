package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.Contest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContestPagingAndSortingRepo extends PagingAndSortingRepository<Contest, String> {

}
