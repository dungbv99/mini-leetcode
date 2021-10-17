package com.hust.minileetcode.controller;

import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.TestCase;
import com.hust.minileetcode.model.*;
import com.hust.minileetcode.service.ProblemTestCaseService;
import com.hust.minileetcode.utils.AnswerChecking;
import com.hust.minileetcode.utils.ComputerLanguage;
import com.hust.minileetcode.utils.TempDir;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ContestProblemController {
    ProblemTestCaseService problemTestCaseService;
//    AnswerChecking answerChecking;
    TempDir tempDir = new TempDir();

    @PostMapping("/create-contest-problem")
    public ResponseEntity<?> createContestProblem(@RequestBody ModelCreateContestProblem modelCreateContestProblem) throws Exception{
        System.out.println(modelCreateContestProblem.toString());
        try {
            problemTestCaseService.createContestProblem(modelCreateContestProblem);
            return ResponseEntity.status(200).body(null);
        } catch (Exception e) {
            log.info("err ");
            System.out.println(e.getMessage());
            throw new Exception(e.toString());
        }
    }

    @PostMapping("/update-contest-problem/{problemId}")
    public ResponseEntity<?> updateContestProblem(@RequestBody ModelCreateContestProblem modelCreateContestProblem, @PathVariable("problemId") String problemId) throws Exception{
        problemTestCaseService.updateContestProblem(modelCreateContestProblem, problemId);
        return ResponseEntity.ok(null);
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
        TestCase testCase = problemTestCaseService.createTestCase(modelCreateTestCase, problemId);
        return ResponseEntity.status(200).body(testCase);
    }

    @PostMapping("/get-test-case-result/{problemId}")
    public ResponseEntity<?> getTestCaseResult(@PathVariable("problemId") String problemId, @RequestBody ModelGetTestCaseResult testCaseResult, Principal principal) throws Exception {
        log.info("get test case result {}", problemId);
        String testcaseResult = problemTestCaseService.getTestCaseResult(problemId, principal.getName(), testCaseResult);
        log.info("testcaseResult {}", testcaseResult);
        ModelGetTestCaseResultResponse resp = ModelGetTestCaseResultResponse.builder().result(testcaseResult).build();
        return ResponseEntity.status(200).body(resp);
    }

    @PostMapping("/edit-test-case/{testCaseId}")
    public ResponseEntity<?> editTestCase(@PathVariable("testCaseId") UUID testCaseId, @RequestBody ModelCreateTestCase modelCreateTestCase) throws Exception {
        TestCase testCase = problemTestCaseService.updateTestCase(modelCreateTestCase, testCaseId);
        return ResponseEntity.status(200).body(testCase);
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

    @PostMapping("/ide/{computerLanguage}")
    public ResponseEntity<?> runCode(@PathVariable("computerLanguage") String computerLanguage, @RequestBody ModelRunCodeFromIDE modelRunCodeFromIDE, Principal principal) throws Exception{
        String response = null;
        response = problemTestCaseService.executableIDECode(modelRunCodeFromIDE,principal.getName(), computerLanguage);
        ModelRunCodeFromIDEOutput modelRunCodeFromIDEOutput = new ModelRunCodeFromIDEOutput();
        modelRunCodeFromIDEOutput.setOutput(response);
        return ResponseEntity.status(200).body(modelRunCodeFromIDEOutput);
    }

    @GetMapping("/problem-details/{problemId}")
    public ResponseEntity<?> getProblemDetails(@PathVariable("problemId") String problemId) throws Exception {
        ContestProblem contestProblem = problemTestCaseService.getContestProblem(problemId);
        return ResponseEntity.status(200).body(contestProblem);
    }

    @PostMapping("/problem-detail-run-code/{problemId}")
    public ResponseEntity<?> problemDetailsRunCode(@PathVariable("problemId") String problemId, @RequestBody ModelProblemDetailRunCode modelProblemDetailRunCode, Principal principal) throws Exception {
        ModelProblemDetailRunCodeResponse resp = problemTestCaseService.problemDetailRunCode(problemId, modelProblemDetailRunCode, principal.getName());
        return ResponseEntity.ok(resp);
    }

}
