package com.hust.minileetcode.controller;

import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.TestCase;
import com.hust.minileetcode.model.ModelAddProblemLanguageSourceCode;
import com.hust.minileetcode.model.ModelCreateContestProblem;
import com.hust.minileetcode.model.ModelCreateTestCase;
import com.hust.minileetcode.service.ProblemTestCaseService;
import com.hust.minileetcode.utils.AnswerChecking;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class ContestProblemController {
    ProblemTestCaseService problemTestCaseService;
    AnswerChecking answerChecking;

    @PostMapping("/create-contest-problem")
    public ResponseEntity<?> createContestProblem(@RequestBody ModelCreateContestProblem modelCreateContestProblem) throws Exception{
        try {
            problemTestCaseService.createContestProblem(modelCreateContestProblem);
            return ResponseEntity.status(200).body("ok");
        } catch (Exception e) {
            throw new Exception(e.toString());
        }

    }

    @PostMapping("/add-problem-language-source-code/{problemId}")
    public ResponseEntity<?> addProblemLanguageSourceCode(
            @PathVariable("problemId") String problemId,
            @RequestBody ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode) throws Exception{
            try {
                problemTestCaseService.updateProblemSourceCode(modelAddProblemLanguageSourceCode, problemId);
                return ResponseEntity.status(200).body(null);
            }catch (Exception e){
                throw new Exception(e.toString());
            }
    }


    @PostMapping("/update-test-case-and-generate-answer/{problemId}")
    public ResponseEntity<?> createTestCase(@RequestBody ModelCreateTestCase modelCreateTestCase, @PathVariable("problemId") String problemId) throws Exception{
        try{
            String correctAnswer = problemTestCaseService.createTestCase(modelCreateTestCase,problemId);
            if(answerChecking.checkAnswerGenerate(correctAnswer)){
                TestCase testCase = new TestCase();
                testCase.setTestCase(modelCreateTestCase.getTestCase());
                testCase.setTestCasePoint(modelCreateTestCase.getTestCasePoint());
                testCase.setCorrectAnswer(correctAnswer);
                ContestProblem contestProblem = problemTestCaseService.findContestProblemByProblemId(problemId);
                testCase.setContestProblem(contestProblem);
                problemTestCaseService.saveTestCase(testCase);
            }else{

            }
            return ResponseEntity.status(200).body(correctAnswer);
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }

    @GetMapping("/get-contest-problem-paging")
    public ResponseEntity<?> getContestProblemPaging(Pageable pageable) throws Exception {
        try {
            Page<ContestProblem> contestProblemPage = problemTestCaseService.getContestProblemPaging(pageable);
            return ResponseEntity.status(200).body(contestProblemPage);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.toString());
        }
    }



}
