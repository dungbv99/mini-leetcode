package com.hust.minileetcode.controller;

import com.hust.minileetcode.model.ModelAddProblemLanguageSourceCode;
import com.hust.minileetcode.model.ModelCreateContestProblem;
import com.hust.minileetcode.model.ModelCreateTestCase;
import com.hust.minileetcode.model.ModelCreateTestCaseResponse;
import com.hust.minileetcode.service.ProblemTestCaseService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class ContestProblemController {
    ProblemTestCaseService problemTestCaseService;

    @PostMapping("/create-contest-problem")
    public ResponseEntity<?> createContestProblem(@RequestBody ModelCreateContestProblem modelCreateContestProblem){
        log.info(modelCreateContestProblem.toString());
        try {
            problemTestCaseService.createContestProblem(modelCreateContestProblem);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e);
        }
        return ResponseEntity.status(200).body("ok");
    }

    @PostMapping("/add-problem-language-source-code/{problemId}")
    public ResponseEntity<?> addProblemLanguageSourceCode(
            @PathVariable("problemId") String problemId,
            @RequestBody ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode){
        problemTestCaseService.updateProblemSourceCode(modelAddProblemLanguageSourceCode, problemId);
        return ResponseEntity.status(200).body(null);
    }


    @PostMapping("/update-test-case-and-generate-answer/{problemId}")
    public ResponseEntity<?> createTestCase(@RequestBody ModelCreateTestCase modelCreateTestCase, @PathVariable("problemId") String problemId) throws Exception{
        ModelCreateTestCaseResponse modelCreateTestCaseResponse = new ModelCreateTestCaseResponse();
        modelCreateTestCaseResponse.setCorrectTestCase(null);
        modelCreateTestCaseResponse.setException(null);
        String correctAnswer;
//        try {
//            correctAnswer = problemTestCaseService.createTestCase(modelCreateTestCase,problemId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            modelCreateTestCaseResponse.setException(e.toString());
//            return ResponseEntity.status(400).body(modelCreateTestCase);
//        }
        correctAnswer = problemTestCaseService.createTestCase(modelCreateTestCase,problemId);
        modelCreateTestCaseResponse.setCorrectTestCase(correctAnswer);
        return ResponseEntity.status(200).body(modelCreateTestCaseResponse);
    }



}
