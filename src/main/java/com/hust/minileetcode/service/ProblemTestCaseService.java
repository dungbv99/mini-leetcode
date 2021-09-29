package com.hust.minileetcode.service;

import com.hust.minileetcode.model.ModelAddProblemLanguageSourceCode;
import com.hust.minileetcode.model.ModelCreateContestProblem;
import com.hust.minileetcode.model.ModelCreateTestCase;

public interface ProblemTestCaseService {
    void createContestProblem(ModelCreateContestProblem modelCreateContestProblem) throws Exception;

    void updateProblemSourceCode(ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode, String problemId);

    String createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId) throws Exception;
}
