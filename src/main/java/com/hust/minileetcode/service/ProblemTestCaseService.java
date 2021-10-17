package com.hust.minileetcode.service;

import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.TestCase;
import com.hust.minileetcode.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProblemTestCaseService {
    void createContestProblem(ModelCreateContestProblem modelCreateContestProblem) throws Exception;

    void updateContestProblem(ModelCreateContestProblem modelCreateContestProblem, String problemId) throws Exception;

    void updateProblemSourceCode(ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode, String problemId);

    TestCase createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId) throws Exception;

    TestCase updateTestCase(ModelCreateTestCase modelCreateTestCase, UUID testCaseId) throws Exception;

    Page<ContestProblem> getContestProblemPaging(Pageable pageable);

    ContestProblem findContestProblemByProblemId(String problemId) throws Exception;

    void saveTestCase(TestCase testCase) throws Exception;

    String executableIDECode(ModelRunCodeFromIDE modelRunCodeFromIDE, String userName, String computerLanguage) throws Exception;

    ContestProblem getContestProblem(String problemId) throws Exception;

    ModelProblemDetailRunCodeResponse problemDetailRunCode(String problemId, ModelProblemDetailRunCode modelProblemDetailRunCode, String userName) throws Exception;
}
