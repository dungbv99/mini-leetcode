package com.hust.minileetcode.controller;

import com.hust.minileetcode.entity.ProblemEntity;
import com.hust.minileetcode.entity.TestCaseEntity;
import com.hust.minileetcode.exception.MiniLeetCodeException;
import com.hust.minileetcode.model.*;
import com.hust.minileetcode.service.ProblemTestCaseService;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ContestProblemController {
    ProblemTestCaseService problemTestCaseService;

    @PostMapping("/create-problem")
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
        ProblemEntity problemEntity = problemTestCaseService.updateContestProblem(modelCreateContestProblem, problemId);
        return ResponseEntity.ok(problemEntity);
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
        TestCaseEntity testCaseEntity = problemTestCaseService.createTestCase(modelCreateTestCase, problemId);
        return ResponseEntity.status(200).body(testCaseEntity);
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
        TestCaseEntity testCaseEntity = problemTestCaseService.updateTestCase(modelCreateTestCase, testCaseId);
        return ResponseEntity.status(200).body(testCaseEntity);
    }

    @GetMapping("/get-contest-problem-paging")
    public ResponseEntity<?> getContestProblemPaging(Pageable pageable, @Param("sortBy") String sortBy, @Param("desc") String desc) throws Exception {
        log.info("getContestProblemPaging pageable {}", pageable);
        log.info("sortBy {}", sortBy);
        try {
            if(sortBy != null){
                if(desc != null)
                    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy).descending());
                else
                    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy).ascending());
            }
            Page<ProblemEntity> contestProblemPage = problemTestCaseService.getContestProblemPaging(pageable);
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
        log.info("getProblemDetails problemId ", problemId);
        ProblemEntity problemEntity = problemTestCaseService.getContestProblem(problemId);
        return ResponseEntity.status(200).body(problemEntity);
    }

    @PostMapping("/update-problem-detail/{problemId}")
    public ResponseEntity<?> updateProblemDetails(@RequestBody ModelCreateContestProblem modelCreateContestProblem, @PathVariable("problemId") String problemId) throws Exception {
        log.info("updateProblemDetails problemId {}", problemId);
        ProblemEntity problemEntity = problemTestCaseService.updateContestProblem(modelCreateContestProblem, problemId);
        return ResponseEntity.status(200).body(problemEntity);
    }

    @PostMapping("/problem-detail-run-code/{problemId}")
    public ResponseEntity<?> problemDetailsRunCode(@PathVariable("problemId") String problemId, @RequestBody ModelProblemDetailRunCode modelProblemDetailRunCode, Principal principal) throws Exception {
        ModelProblemDetailRunCodeResponse resp = problemTestCaseService.problemDetailRunCode(problemId, modelProblemDetailRunCode, principal.getName());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/check-compile")
    public ResponseEntity<?> checkCompile(@RequestBody ModelCheckCompile modelCheckCompile, Principal principal) throws Exception {
        String resp = problemTestCaseService.checkCompile(modelCheckCompile, principal.getName());
        ModelCheckCompileResponse modelCheckCompileResponse = ModelCheckCompileResponse.builder().status(resp).build();
        return ResponseEntity.status(200).body(modelCheckCompileResponse);
    }

    @PostMapping("/save-test-case/{problemId}")
    public ResponseEntity<?> saveTestCase(@PathVariable("problemId") String problemId, @RequestBody ModelSaveTestcase modelSaveTestcase){
        TestCaseEntity testCaseEntity = problemTestCaseService.saveTestCase(problemId, modelSaveTestcase);
        return ResponseEntity.status(200).body(testCaseEntity);
    }

    @PostMapping("/problem-details-submission/{problemId}")
    public ResponseEntity<?> problemDetailsSubmission(@PathVariable("problemId") String problemId, @RequestBody ModelProblemDetailSubmission modelProblemDetailSubmission, Principal principal) throws Exception {
        log.info("problemDetailsSubmission {}", problemId);
        ModelProblemSubmissionResponse response = problemTestCaseService.problemDetailSubmission(modelProblemDetailSubmission, problemId, principal.getName());
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/get-all-problem-submission-by-user/{problemId}")
    public ResponseEntity<?> getAllProblemSubmissionByUser(@PathVariable("problemId") String problemId, Principal principal) throws Exception {
        ListProblemSubmissionResponse listProblemSubmissionResponse = problemTestCaseService.getListProblemSubmissionResponse(problemId, principal.getName());
        return ResponseEntity.status(200).body(listProblemSubmissionResponse);
    }

    @GetMapping("/get-problem-submission/{id}")
    public ResponseEntity<?> getProblemSubmissionById(@PathVariable("id") UUID id, Principal principal) throws MiniLeetCodeException {
        log.info("getProblemSubmissionById id {}", id);
        ModelProblemSubmissionDetailResponse modelProblemSubmissionDetailResponse = problemTestCaseService.findProblemSubmissionById(id, principal.getName());
        return ResponseEntity.status(200).body(modelProblemSubmissionDetailResponse);
    }

    @PostMapping("/create-contest")
    public ResponseEntity<?> createContest(@RequestBody ModelCreateContest modelCreateContest, Principal principal) throws Exception {
        log.info("createContest");
        problemTestCaseService.createContest(modelCreateContest, principal.getName());
        return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/get-contest-paging")
    public ResponseEntity<?> getContestPaging(Pageable pageable, @Param("sortBy") String sortBy){
        log.info("getContestPaging sortBy {} pageable {}", sortBy, pageable);
        if(sortBy != null){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy));
        }else{
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").ascending());
        }
        ModelGetContestPageResponse modelGetContestPageResponse = problemTestCaseService.getContestPaging(pageable);
        return ResponseEntity.status(200).body(modelGetContestPageResponse);
    }

    @GetMapping("/get-contest-detail/{contestId}")
    public ResponseEntity<?> getContestDetail(@PathVariable("contestId") String contestId){
        log.info("getContestDetail");
        ModelGetContestDetailResponse response = problemTestCaseService.getContestDetailByContestId(contestId);
        return ResponseEntity.status(200).body(response);
    }

    @Secured("ROLE_STUDENT")
    @PostMapping("/student-register-contest/{contestId}")
    public ResponseEntity<?> studentRegisterContest(@PathVariable("contestId") String contestId, Principal principal) throws MiniLeetCodeException {
        log.info("studentRegisterContest {}", contestId);
        ModelStudentRegisterContestResponse resp = problemTestCaseService.studentRegisterContest(contestId, principal.getName());
        return ResponseEntity.status(200).body(resp);
    }

    @Secured("ROLE_TEACHER")
    @GetMapping("/get-contest-paging-by-user-create")
    public ResponseEntity<?> getContestPagingByUserCreate(Principal principal, Pageable pageable, @Param("sortBy") String sortBy){
        log.info("getContestPagingByUserCreate");
        if(sortBy != null){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy));
        }else{
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").ascending());
        }
        ModelGetContestPageResponse resp = problemTestCaseService.getContestPagingByUserCreatedContest(principal.getName(), pageable);
        return ResponseEntity.status(200).body(resp);
    }

    @Secured("ROLE_TEACHER")
    @GetMapping("/get-user-register-successful-contest/{contestId}")
    public ResponseEntity<?> getUserRegisterSuccessfulContest(@PathVariable("contestId") String contestId, Pageable pageable){
        log.info("get User Register Successful Contest ");
        ListModelUserRegisteredContestInfo resp = problemTestCaseService.getListUserRegisterContestSuccessfulPaging(pageable, contestId);
        return ResponseEntity.status(200).body(resp);
    }


    @Secured("ROLE_TEACHER")
    @GetMapping("/get-user-register-pending-contest/{contestId}")
    public ResponseEntity<?> getUserRegisterPendingContest(@PathVariable("contestId") String contestId, Pageable pageable, @Param("size") String size, @Param("page") String page){
        log.info("get User Register Pending Contest pageable {} size {} page {} contest id {}", pageable, size, page, contestId);
        ListModelUserRegisteredContestInfo resp = problemTestCaseService.getListUserRegisterContestPendingPaging(pageable, contestId);
        return ResponseEntity.status(200).body(resp);
    }

    @Secured("ROLE_TEACHER")
    @PostMapping("/techer-manager-student-register-contest")
    public ResponseEntity<?> teacherManagerStudentRegisterContest(Principal principal, @RequestBody ModelTeacherManageStudentRegisterContest request) throws MiniLeetCodeException {
        log.info("teacherManagerStudentRegisterContest");
        problemTestCaseService.teacherManageStudentRegisterContest(principal.getName(), request);
        return ResponseEntity.status(200).body(null);
    }


    @GetMapping("/get-contest-paging-registered")
    public ResponseEntity<?> getContestRegisteredByStudentPaging(Pageable pageable, @Param("sortBy") String sortBy, Principal principal){
        log.info("getContestRegisteredByStudentPaging sortBy {} pageable {}", sortBy, pageable);
        if(sortBy != null){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy));
        }else{
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").ascending());
        }
        ModelGetContestPageResponse modelGetContestPageResponse = problemTestCaseService.getRegisteredContestByUser(pageable, principal.getName());
        return ResponseEntity.status(200).body(modelGetContestPageResponse);
    }

    @GetMapping("/get-contest-paging-not-registered")
    public ResponseEntity<?> getContestNotRegisteredByStudentPaging(Pageable pageable, @Param("sortBy") String sortBy, Principal principal){
        log.info("getContestRegisteredByStudentPaging sortBy {} pageable {}", sortBy, pageable);
        if(sortBy != null){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy));
        }else{
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").ascending());
        }
        ModelGetContestPageResponse modelGetContestPageResponse = problemTestCaseService.getNotRegisteredContestByUser(pageable, principal.getName());
        return ResponseEntity.status(200).body(modelGetContestPageResponse);
    }

    @PostMapping("/contest-submit-problem")
    public ResponseEntity<?> contestSubmitProblem(@RequestBody ModelContestSubmission request, Principal principal) throws Exception {
        log.info("/contest-submit-problem");
        ModelContestSubmissionResponse resp = problemTestCaseService.submitContestProblem(request, principal.getName());
        log.info("resp {}", resp);
        return ResponseEntity.status(200).body(resp);
    }

}
