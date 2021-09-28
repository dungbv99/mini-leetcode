package com.hust.minileetcode.controller;

import com.hust.minileetcode.model.ModelCreateContestProblem;
import com.hust.minileetcode.model.ModelCreateTestCase;
import com.hust.minileetcode.service.ProblemTestCaseService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class ContestProblemController {
    ProblemTestCaseService problemTestCaseService;

    @PostMapping("/create-contest-problem")
    public ResponseEntity<?> createContestProblem(@RequestBody ModelCreateContestProblem modelCreateContestProblem){
        problemTestCaseService.createContestProblem(modelCreateContestProblem);
        return ResponseEntity.ok().body(null);
    }
    @PostMapping("/create-test-case/{problemId}")
    public ResponseEntity<?> createTestCase(@RequestBody ModelCreateTestCase modelCreateTestCase, @PathVariable("problemId") String problemId){

        return ResponseEntity.ok(null);
    }
}
