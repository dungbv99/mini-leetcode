package com.hust.minileetcode.service;

import com.hust.minileetcode.model.ModelCreateContestProblem;
import com.hust.minileetcode.model.ModelCreateTestCase;

public interface ProblemTestCaseService {
    public void createContestProblem(ModelCreateContestProblem modelCreateContestProblem);

    public String createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId, String userName);
}
