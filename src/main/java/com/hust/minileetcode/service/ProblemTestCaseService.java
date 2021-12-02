package com.hust.minileetcode.service;

import com.hust.minileetcode.entity.ContestEntity;
import com.hust.minileetcode.entity.ProblemEntity;
import com.hust.minileetcode.entity.TestCaseEntity;
import com.hust.minileetcode.exception.MiniLeetCodeException;
import com.hust.minileetcode.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProblemTestCaseService {

    void createContestProblem(ModelCreateContestProblem modelCreateContestProblem) throws Exception;

    ProblemEntity updateContestProblem(ModelCreateContestProblem modelCreateContestProblem, String problemId) throws Exception;

    void updateProblemSourceCode(ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode, String problemId);

    TestCaseEntity createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId) throws Exception;

    TestCaseEntity updateTestCase(ModelCreateTestCase modelCreateTestCase, UUID testCaseId) throws Exception;

    Page<ProblemEntity> getContestProblemPaging(Pageable pageable) throws Exception;

    ProblemEntity findContestProblemByProblemId(String problemId) throws Exception;

    void saveTestCase(TestCaseEntity testCase) throws Exception;

    String executableIDECode(ModelRunCodeFromIDE modelRunCodeFromIDE, String userName, String computerLanguage) throws Exception;

    ProblemEntity getContestProblem(String problemId) throws Exception;

    ModelProblemDetailRunCodeResponse problemDetailRunCode(String problemId, ModelProblemDetailRunCode modelProblemDetailRunCode, String userName) throws Exception;

    String getTestCaseResult(String problemId, String userName, ModelGetTestCaseResult modelGetTestCaseResult) throws Exception;

    String checkCompile(ModelCheckCompile modelCheckCompile, String userName) throws Exception;

    TestCaseEntity saveTestCase(String problemId, ModelSaveTestcase modelSaveTestcase);

    ModelProblemSubmissionResponse problemDetailSubmission(ModelProblemDetailSubmission modelProblemDetailSubmission, String problemId, String userName) throws Exception;

    ListProblemSubmissionResponse getListProblemSubmissionResponse(String problemId, String userId) throws Exception;

    ContestEntity createContest(ModelCreateContest modelCreateContest, String userName) throws Exception;

    ContestEntity updateContest(ModelUpdateContest modelUpdateContest, String userName, String contestId) throws Exception;

    ModelProblemSubmissionDetailResponse findProblemSubmissionById(UUID id, String userName) throws MiniLeetCodeException;

    ModelGetContestPageResponse getContestPaging(Pageable pageable);

    ModelGetContestDetailResponse getContestDetailByContestId(String contestId);

    ModelContestSubmissionResponse submitContestProblem(ModelContestSubmission modelContestSubmission, String userName) throws Exception;

    ModelStudentRegisterContestResponse studentRegisterContest(String contestId, String userId) throws MiniLeetCodeException;

    void teacherManageStudentRegisterContest(String teacherId, ModelTeacherManageStudentRegisterContest modelTeacherManageStudentRegisterContest) throws MiniLeetCodeException;

    void calculateContestResult(String contestId);

    ModelGetContestPageResponse getContestPagingByUserCreatedContest(String userName, Pageable pageable);

    ListModelUserRegisteredContestInfo getListUserRegisterContestSuccessfulPaging(Pageable pageable, String contestId);

    ListModelUserRegisteredContestInfo getListUserRegisterContestPendingPaging(Pageable pageable, String contestId);
}
