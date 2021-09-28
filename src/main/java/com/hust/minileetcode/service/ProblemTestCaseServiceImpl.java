package com.hust.minileetcode.service;

import com.hust.minileetcode.docker.DockerClientBase;
import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.ProblemSourceCode;
import com.hust.minileetcode.model.ModelCreateContestProblem;
import com.hust.minileetcode.model.ModelCreateTestCase;
import com.hust.minileetcode.repo.ContestProblemRepo;
import com.hust.minileetcode.repo.ProblemSourceCodeRepo;
import com.hust.minileetcode.repo.TestCaseRepo;
import com.hust.minileetcode.utils.TempDir;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProblemTestCaseServiceImpl implements ProblemTestCaseService {
    private ContestProblemRepo contestProblemRepo;
    private TestCaseRepo testCaseRepo;
    private ProblemSourceCodeRepo problemSourceCodeRepo;
    private DockerClientBase dockerClientBase = new DockerClientBase();
    private TempDir tempDir = new TempDir();
    @Override
    public void createContestProblem(ModelCreateContestProblem modelCreateContestProblem) {
        ContestProblem contestProblem = new ContestProblem();
        contestProblem.setProblemId(modelCreateContestProblem.getProblemId());
        contestProblem.setProblemName(modelCreateContestProblem.getProblemName());
        contestProblem.setProblemDescription(modelCreateContestProblem.getProblemDescription());
        contestProblem.setCategoryId(modelCreateContestProblem.getCategoryId());
        contestProblem.setLevelId(modelCreateContestProblem.getLevelId());
        contestProblem.setMemoryLimit(modelCreateContestProblem.getMemoryLimit());
        contestProblem.setTimeLimit(modelCreateContestProblem.getTimeLimit());
        contestProblemRepo.save(contestProblem);
    }

    @Override
    public String createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId, String userName) {
        ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
//        ProblemSourceCode problemSourceCode = contestProblem.getProblemSourceCode();
//        String source = problemSourceCode.createSolutionSourceCode();
//        String tempName = tempDir.createRandomScriptFileName(contestProblem.getProblemName()+"-"+userName);
//        contestProblem.getProblemSourceCode()
        return "";
    }
}
