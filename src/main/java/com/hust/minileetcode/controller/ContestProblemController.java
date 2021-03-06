package com.hust.minileetcode.controller;

import com.hust.minileetcode.entity.ContestSubmissionEntity;
import com.hust.minileetcode.entity.ProblemEntity;
import com.hust.minileetcode.entity.TestCaseEntity;
import com.hust.minileetcode.entity.UserSubmissionContestResultNativeEntity;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ContestProblemController {
    ProblemTestCaseService problemTestCaseService;

    @PostMapping("/create-problem")
    public ResponseEntity<?> createContestProblem(@RequestBody ModelCreateContestProblem modelCreateContestProblem, Principal principal) throws MiniLeetCodeException {
        log.info("create problem {}", modelCreateContestProblem);
        problemTestCaseService.createContestProblem(modelCreateContestProblem, principal.getName());
        return ResponseEntity.status(200).body(null);
    }


    @PostMapping("/add-problem-language-source-code/{problemId}")
    public ResponseEntity<?> addProblemLanguageSourceCode(
            @PathVariable("problemId") String problemId,
            @RequestBody ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode) throws Exception{
        problemTestCaseService.updateProblemSourceCode(modelAddProblemLanguageSourceCode, problemId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @PostMapping("/get-test-case-result/{problemId}")
    public ResponseEntity<?> getTestCaseResult(@PathVariable("problemId") String problemId, @RequestBody ModelGetTestCaseResult testCaseResult, Principal principal) throws Exception {
        log.info("get test case result {}", problemId);
        ModelGetTestCaseResultResponse resp = problemTestCaseService.getTestCaseResult(problemId, principal.getName(), testCaseResult);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }



    @GetMapping("/get-contest-problem-paging")
    public ResponseEntity<?> getContestProblemPaging(Pageable pageable, @Param("sortBy") String sortBy, @Param("desc") String desc){
        log.info("getContestProblemPaging pageable {}", pageable);
        log.info("sortBy {}", sortBy);
        if(sortBy != null){
            if(desc != null)
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy).descending());
            else
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy).ascending());
        }
        Page<ProblemEntity> contestProblemPage = problemTestCaseService.getContestProblemPaging(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(contestProblemPage);
    }

    @PostMapping("/ide/{computerLanguage}")
    public ResponseEntity<?> runCode(@PathVariable("computerLanguage") String computerLanguage, @RequestBody ModelRunCodeFromIDE modelRunCodeFromIDE, Principal principal) throws Exception{
        String response = null;
        response = problemTestCaseService.executableIDECode(modelRunCodeFromIDE,principal.getName(), computerLanguage);
        ModelRunCodeFromIDEOutput modelRunCodeFromIDEOutput = new ModelRunCodeFromIDEOutput();
        modelRunCodeFromIDEOutput.setOutput(response);
        return ResponseEntity.status(HttpStatus.OK).body(modelRunCodeFromIDEOutput);
    }

    @GetMapping("/problem-details/{problemId}")
    public ResponseEntity<?> getProblemDetails(@PathVariable("problemId") String problemId) throws Exception {
        log.info("getProblemDetails problemId ", problemId);
        ProblemEntity problemEntity = problemTestCaseService.getContestProblem(problemId);
        return ResponseEntity.status(HttpStatus.OK).body(problemEntity);
    }

    @PostMapping("/update-problem-detail/{problemId}")
    public ResponseEntity<?> updateProblemDetails(@RequestBody ModelCreateContestProblem modelCreateContestProblem, @PathVariable("problemId") String problemId, Principal principal) throws Exception {
        log.info("updateProblemDetails problemId {}", problemId);
        ProblemEntity problemEntity = problemTestCaseService.updateContestProblem(modelCreateContestProblem, problemId, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(problemEntity);
    }

    @PostMapping("/problem-detail-run-code/{problemId}")
    public ResponseEntity<?> problemDetailsRunCode(@PathVariable("problemId") String problemId, @RequestBody ModelProblemDetailRunCode modelProblemDetailRunCode, Principal principal) throws Exception {
        ModelProblemDetailRunCodeResponse resp = problemTestCaseService.problemDetailRunCode(problemId, modelProblemDetailRunCode, principal.getName());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/check-compile")
    public ResponseEntity<?> checkCompile(@RequestBody ModelCheckCompile modelCheckCompile, Principal principal) throws Exception {
        ModelCheckCompileResponse resp = problemTestCaseService.checkCompile(modelCheckCompile, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping("/save-test-case/{problemId}")
    public ResponseEntity<?> saveTestCase(@PathVariable("problemId") String problemId, @RequestBody ModelSaveTestcase modelSaveTestcase){
        TestCaseEntity testCaseEntity = problemTestCaseService.saveTestCase(problemId, modelSaveTestcase);
        return ResponseEntity.status(HttpStatus.OK).body(testCaseEntity);
    }

    @PostMapping("/problem-details-submission/{problemId}")
    public ResponseEntity<?> problemDetailsSubmission(@PathVariable("problemId") String problemId, @RequestBody ModelProblemDetailSubmission modelProblemDetailSubmission, Principal principal) throws Exception {
        log.info("problemDetailsSubmission {}", problemId);
        ModelContestSubmissionResponse response = problemTestCaseService.problemDetailSubmission(modelProblemDetailSubmission, problemId, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-all-problem-submission-by-user/{problemId}")
    public ResponseEntity<?> getAllProblemSubmissionByUser(@PathVariable("problemId") String problemId, Principal principal) throws Exception {
        ListProblemSubmissionResponse listProblemSubmissionResponse = problemTestCaseService.getListProblemSubmissionResponse(problemId, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(listProblemSubmissionResponse);
    }

    @GetMapping("/get-problem-submission/{id}")
    public ResponseEntity<?> getProblemSubmissionById(@PathVariable("id") UUID id, Principal principal) throws MiniLeetCodeException {
        log.info("getProblemSubmissionById id {}", id);
        ModelProblemSubmissionDetailResponse modelProblemSubmissionDetailResponse = problemTestCaseService.findProblemSubmissionById(id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(modelProblemSubmissionDetailResponse);
    }

    @PostMapping("/create-contest")
    public ResponseEntity<?> createContest(@RequestBody ModelCreateContest modelCreateContest, Principal principal) throws Exception {
        log.info("createContest");
        problemTestCaseService.createContest(modelCreateContest, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/edit-contest/{contestId}")
    public ResponseEntity<?> editContest(@RequestBody ModelUpdateContest modelUpdateContest, Principal principal, @PathVariable("contestId") String contestId) throws Exception {
        log.info("edit contest modelUpdateContest {}",modelUpdateContest );
        problemTestCaseService.updateContest(modelUpdateContest, principal.getName(), contestId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
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
        return ResponseEntity.status(HttpStatus.OK).body(modelGetContestPageResponse);
    }

    @GetMapping("/get-contest-detail/{contestId}")
    public ResponseEntity<?> getContestDetail(@PathVariable("contestId") String contestId, Principal principal){
        log.info("getContestDetail constestid {}", contestId);
        ModelGetContestDetailResponse response = problemTestCaseService.getContestDetailByContestIdAndTeacher(contestId, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-contest-detail-solving/{contestId}")
    public ResponseEntity<?> getContestDetailSolving(@PathVariable("contestId") String contestId, Principal principal){
        log.info("getContestDetail constestid {}", contestId);
        ModelGetContestDetailResponse response = problemTestCaseService.getContestSolvingDetailByContestId(contestId, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Secured("ROLE_STUDENT")
    @PostMapping("/student-register-contest/{contestId}")
    public ResponseEntity<?> studentRegisterContest(@PathVariable("contestId") String contestId, Principal principal) throws MiniLeetCodeException {
        log.info("studentRegisterContest {}", contestId);
        ModelStudentRegisterContestResponse resp = problemTestCaseService.studentRegisterContest(contestId, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(resp);
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
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @Secured("ROLE_TEACHER")
    @GetMapping("/get-user-register-successful-contest/{contestId}")
    public ResponseEntity<?> getUserRegisterSuccessfulContest(@PathVariable("contestId") String contestId, Pageable pageable){
        log.info("get User Register Successful Contest ");
        ListModelUserRegisteredContestInfo resp = problemTestCaseService.getListUserRegisterContestSuccessfulPaging(pageable, contestId);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }


    @Secured("ROLE_TEACHER")
    @GetMapping("/get-user-register-pending-contest/{contestId}")
    public ResponseEntity<?> getUserRegisterPendingContest(@PathVariable("contestId") String contestId, Pageable pageable, @Param("size") String size, @Param("page") String page){
        log.info("get User Register Pending Contest pageable {} size {} page {} contest id {}", pageable, size, page, contestId);
        ListModelUserRegisteredContestInfo resp = problemTestCaseService.getListUserRegisterContestPendingPaging(pageable, contestId);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @Secured("ROLE_TEACHER")
    @GetMapping("/search-user/{contestId}")
    public ResponseEntity<?> searchUser(@PathVariable("contestId") String contestId, Pageable pageable, @Param("keyword") String keyword){
        if(keyword == null){
            keyword = "";
        }
        ListModelUserRegisteredContestInfo resp = problemTestCaseService.searchUser(pageable, contestId, keyword);
        return ResponseEntity.status(HttpStatus.OK).body(resp);

    }



    @Secured("ROLE_TEACHER")
    @PostMapping("/techer-manager-student-register-contest")
    public ResponseEntity<?> teacherManagerStudentRegisterContest(Principal principal, @RequestBody ModelTeacherManageStudentRegisterContest request) throws MiniLeetCodeException {
        log.info("teacherManagerStudentRegisterContest");
        problemTestCaseService.teacherManageStudentRegisterContest(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.OK).body(null);
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
        return ResponseEntity.status(HttpStatus.OK).body(modelGetContestPageResponse);
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
        return ResponseEntity.status(HttpStatus.OK).body(modelGetContestPageResponse);
    }

    @PostMapping("/contest-submit-problem")
    public ResponseEntity<?> contestSubmitProblem(@RequestBody ModelContestSubmission request, Principal principal) throws Exception {
        log.info("/contest-submit-problem");
        ModelContestSubmissionResponse resp = problemTestCaseService.submitContestProblem(request, principal.getName());
        log.info("resp {}", resp);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping("/contest-submit-all")
    public ResponseEntity<?> contestSubmitAll (@RequestBody ModelContestSubmissionAll request, Principal principal){
        log.info("contestSubmitAll request {}", request);
        request.getContents().parallelStream().forEach(modelContestSubmission -> {
            try {
                problemTestCaseService.submitContestProblem(modelContestSubmission, principal.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/get-ranking-contest/{contestId}")
    public ResponseEntity<?> getRankingContest(@PathVariable("contestId") String contestId, Pageable pageable){
        log.info("getRankingContest page {}", pageable);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("point").descending());
        Page<UserSubmissionContestResultNativeEntity> page = problemTestCaseService.getRankingByContestId(pageable, contestId);
        log.info("ranking page {}", page);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
    
    @PostMapping("/recalculate-ranking/{contestId}")
    public ResponseEntity<?> recalculateRanking(@PathVariable("contestId") String contestId){
        log.info("/recalculate-ranking/ contestid {}", contestId);
        problemTestCaseService.calculateContestResult(contestId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/get-problem-public-paging")
    public ResponseEntity<?> getProblemPublicPaging(Pageable pageable){
        Page<ProblemEntity> page = problemTestCaseService.getPublicProblemPaging(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/get-test-case-list-by-problem/{problemId}")
    public ResponseEntity<?> getTestCaseListByProblem(@PathVariable("problemId") String problemId){
        List<ModelGetTestCase> list = problemTestCaseService.getTestCaseByProblem(problemId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/get-test-case-detail/{testCaseId}")
    public ResponseEntity<?> getTestCaseDetail(@PathVariable("testCaseId") UUID testCaseId) throws MiniLeetCodeException {
        ModelGetTestCaseDetail resp = problemTestCaseService.getTestCaseDetail(testCaseId);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping("/update-test-case/{testCaseId}")
    public ResponseEntity<?> updateDateTestCase(@PathVariable("testCaseId") UUID testCaseId, @RequestBody ModelSaveTestcase modelSaveTestcase) throws MiniLeetCodeException {
        problemTestCaseService.editTestCase(testCaseId, modelSaveTestcase);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/add-user-to-contest")
    public ResponseEntity<?> addUserContest(@RequestBody ModelAddUserToContest modelAddUserToContest){
        problemTestCaseService.addUserToContest(modelAddUserToContest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/delete-user-contest")
    public ResponseEntity<?> deleteUserFromContest(@RequestBody ModelAddUserToContest modelAddUserToContest) throws MiniLeetCodeException {
        problemTestCaseService.deleteUserContest(modelAddUserToContest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/get-contest-submission-paging/{contestId}")
    public ResponseEntity<?> getContestSubmissionPaging(@PathVariable("contestId") String contestId, Pageable pageable){
        log.info("getContestSubmissionPaging");
        pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("createdAt").descending());
        Page<ContestSubmission> page = problemTestCaseService.findContestSubmissionByContestIdPaging(pageable, contestId);
        log.info("page {}", page);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/get-contest-problem-submission-detail/{submissionId}")
    public ResponseEntity<?> getContestSubmissionDetail(@PathVariable("submissionId") UUID submissionId){
        log.info("get contest submission detail");
        ContestSubmissionEntity contestSubmission = problemTestCaseService.getContestSubmissionDetail(submissionId);
        return ResponseEntity.status(HttpStatus.OK).body(contestSubmission);
    }

//    @DeleteMapping("/delete-contest/{contestId}")
//    public ResponseEntity<?> deleteContest(@PathVariable("contestId") String contestId, Principal principal) throws MiniLeetCodeException {
//        log.info("delete-contest {}", contestId);
//        problemTestCaseService.deleteContest(contestId, principal.getName());
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }
//
//    @DeleteMapping("/delete-problem/{problemId}")
//    public ResponseEntity<?> deleteProblem(@PathVariable("problemId") String problemId, Principal principal) throws MiniLeetCodeException {
//        log.info("delete-problem {}", problemId);
//        problemTestCaseService.deleteProblem(problemId, principal.getName());
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//
//    }

    @DeleteMapping("/delete-test-case/{testCaseId}")
    public ResponseEntity<?> deleteTestCase(@PathVariable("testCaseId") UUID testCaseId, Principal principal) throws MiniLeetCodeException {
        problemTestCaseService.deleteTestcase(testCaseId, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
