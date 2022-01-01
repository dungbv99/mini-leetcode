package com.hust.minileetcode.repo;

import com.hust.minileetcode.entity.ProblemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface ProblemPagingAndSortingRepo extends PagingAndSortingRepository<ProblemEntity, String> {
    @Query("select p.problemName from ProblemEntity p")
    ArrayList<String> getProblemNamePaging(Pageable pageable);

    Page<ProblemEntity> findAllByPublicIs(Pageable pageable, boolean pub);
}
