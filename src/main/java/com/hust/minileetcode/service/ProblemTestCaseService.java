package com.hust.minileetcode.service;

import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.TestCase;
import com.hust.minileetcode.model.ModelAddProblemLanguageSourceCode;
import com.hust.minileetcode.model.ModelCreateContestProblem;
import com.hust.minileetcode.model.ModelCreateTestCase;
import com.hust.minileetcode.model.ModelRunCodeFromIDE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProblemTestCaseService {
    void createContestProblem(ModelCreateContestProblem modelCreateContestProblem) throws Exception;

    void updateProblemSourceCode(ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode, String problemId);

    String createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId) throws Exception;

    Page<ContestProblem> getContestProblemPaging(Pageable pageable);

    ContestProblem findContestProblemByProblemId(String problemId) throws Exception;

    void saveTestCase(TestCase testCase) throws Exception;

    String executableIDECode(ModelRunCodeFromIDE modelRunCodeFromIDE, String userName, String computerLanguage) throws Exception;
}
