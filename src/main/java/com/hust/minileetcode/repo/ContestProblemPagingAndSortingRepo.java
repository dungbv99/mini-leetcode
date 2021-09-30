package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ContestProblem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

public interface ContestProblemPagingAndSortingRepo extends PagingAndSortingRepository<ContestProblem, String> {
    @Query("select cp.problemName from ContestProblem cp")
    ArrayList<String> getProblemNamePaging(Pageable pageable);
}
