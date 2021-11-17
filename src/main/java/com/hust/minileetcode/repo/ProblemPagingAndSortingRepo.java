package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.Problem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface ProblemPagingAndSortingRepo extends PagingAndSortingRepository<Problem, String> {
    @Query("select p.problemName from Problem p")
    ArrayList<String> getProblemNamePaging(Pageable pageable);

}
