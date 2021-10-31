package com.hust.minileetcode.service;

import com.hust.minileetcode.entity.Contest;
import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.TestCase;
import com.hust.minileetcode.exception.MiniLeetCodeException;
import com.hust.minileetcode.model.*;
import com.spotify.docker.client.exceptions.DockerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.UUID;

public interface ProblemTestCaseService {

    void createContestProblem(ModelCreateContestProblem modelCreateContestProblem) throws Exception;

    void updateContestProblem(ModelCreateContestProblem modelCreateContestProblem, String problemId) throws Exception;

    void updateProblemSourceCode(ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode, String problemId);

    TestCase createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId) throws Exception;

    TestCase updateTestCase(ModelCreateTestCase modelCreateTestCase, UUID testCaseId) throws Exception;

    Page<ContestProblem> getContestProblemPaging(Pageable pageable) throws Exception;

    ContestProblem findContestProblemByProblemId(String problemId) throws Exception;

    void saveTestCase(TestCase testCase) throws Exception;

    String executableIDECode(ModelRunCodeFromIDE modelRunCodeFromIDE, String userName, String computerLanguage) throws Exception;

    ContestProblem getContestProblem(String problemId) throws Exception;

    ModelProblemDetailRunCodeResponse problemDetailRunCode(String problemId, ModelProblemDetailRunCode modelProblemDetailRunCode, String userName) throws Exception;

    String getTestCaseResult(String problemId, String userName, ModelGetTestCaseResult modelGetTestCaseResult) throws Exception;

    String checkCompile(ModelCheckCompile modelCheckCompile, String userName) throws Exception;

    TestCase saveTestCase(String problemId, ModelSaveTestcase modelSaveTestcase);

    ModelProblemDetailSubmissionResponse problemDetailSubmission(ModelProblemDetailSubmission modelProblemDetailSubmission, String problemId, String userName) throws Exception;

    ListProblemSubmissionResponse getListProblemSubmissionResponse(String problemId, String userId) throws Exception;

    Contest createContest(ModelCreateContest modelCreateContest, String userName) throws Exception;

    Contest updateContest(ModelUpdateContest modelUpdateContest, String userName, String contestId) throws Exception;
}
