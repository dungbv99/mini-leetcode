package com.hust.minileetcode.service;

import com.hust.minileetcode.docker.DockerClientBase;
import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.ProblemSourceCode;
import com.hust.minileetcode.entity.TestCase;
import com.hust.minileetcode.model.*;
import com.hust.minileetcode.repo.ContestProblemPagingAndSortingRepo;
import com.hust.minileetcode.repo.ContestProblemRepo;
import com.hust.minileetcode.repo.ProblemSourceCodeRepo;
import com.hust.minileetcode.repo.TestCaseRepo;
import com.hust.minileetcode.utils.ComputerLanguage;
import com.hust.minileetcode.utils.TempDir;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProblemTestCaseServiceImpl implements ProblemTestCaseService {
    private ContestProblemRepo contestProblemRepo;
    private TestCaseRepo testCaseRepo;
    private ProblemSourceCodeRepo problemSourceCodeRepo;
    private DockerClientBase dockerClientBase;
    private TempDir tempDir;
    private ContestProblemPagingAndSortingRepo contestProblemPagingAndSortingRepo;

    @Override
    public void createContestProblem(ModelCreateContestProblem modelCreateContestProblem) throws Exception {
        if(contestProblemRepo.findByProblemId(modelCreateContestProblem.getProblemId()) != null){
            throw new Exception("problem id already exist");
        }
        ContestProblem contestProblem = ContestProblem.builder()
                .problemId(modelCreateContestProblem.getProblemId())
                .problemName(modelCreateContestProblem.getProblemName())
                .problemDescription(modelCreateContestProblem.getProblemDescription())
                .categoryId(modelCreateContestProblem.getCategoryId())
                .memoryLimit(modelCreateContestProblem.getMemoryLimit())
                .timeLimit(modelCreateContestProblem.getTimeLimit())
                .levelId(modelCreateContestProblem.getLevelId())
                .correctSolutionLanguage(modelCreateContestProblem.getCorrectSolutionLanguage())
                .correctSolutionSourceCode(modelCreateContestProblem.getCorrectSolutionSourceCode())
                .solution(modelCreateContestProblem.getSolution())
                .build();
        contestProblemRepo.save(contestProblem);
    }

    @Override
    public void updateContestProblem(ModelCreateContestProblem modelCreateContestProblem, String problemId) throws Exception {
        ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
        if(contestProblem == null){
            throw new Exception("problem id not found");
        }
        contestProblem = ContestProblem.builder()
                .problemId(modelCreateContestProblem.getProblemId())
                .problemName(modelCreateContestProblem.getProblemName())
                .problemDescription(modelCreateContestProblem.getProblemDescription())
                .categoryId(modelCreateContestProblem.getCategoryId())
                .memoryLimit(modelCreateContestProblem.getMemoryLimit())
                .timeLimit(modelCreateContestProblem.getTimeLimit())
                .levelId(modelCreateContestProblem.getLevelId())
                .correctSolutionLanguage(modelCreateContestProblem.getCorrectSolutionLanguage())
                .correctSolutionSourceCode(modelCreateContestProblem.getCorrectSolutionSourceCode())
                .solution(modelCreateContestProblem.getSolution())
                .build();
        contestProblemRepo.save(contestProblem);
    }



    @Override
    public void updateProblemSourceCode(ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode, String problemId) {
        ProblemSourceCode problemSourceCode = new ProblemSourceCode();
        problemSourceCode.setProblemSourceCodeId(modelAddProblemLanguageSourceCode.getProblemSourceCodeId());
        problemSourceCode.setMainSource(modelAddProblemLanguageSourceCode.getMainSource());
        problemSourceCode.setBaseSource(modelAddProblemLanguageSourceCode.getBaseSource());
        problemSourceCode.setLanguage(modelAddProblemLanguageSourceCode.getLanguage());
        problemSourceCode.setProblemFunctionDefaultSource(modelAddProblemLanguageSourceCode.getProblemFunctionDefaultSource());
        problemSourceCode.setProblemFunctionSolution(modelAddProblemLanguageSourceCode.getProblemFunctionSolution());
        ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
        if(contestProblem.getProblemSourceCode() == null){
            ArrayList<ProblemSourceCode> problemSourceCodes = new ArrayList<ProblemSourceCode>();
            problemSourceCodes.add(problemSourceCode);
            contestProblem.setProblemSourceCode(problemSourceCodes);
        }else{
            contestProblem.getProblemSourceCode().add(problemSourceCode);
        }
        problemSourceCodeRepo.save(problemSourceCode);
        contestProblemRepo.save(contestProblem);
    }

    @Override
    public TestCase createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId) throws Exception {
        ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
        if(contestProblem.getProblemSourceCode() == null){
            throw new NullPointerException("problem does not have solution");
        }
        String solution = contestProblem.getCorrectSolutionSourceCode();
        String tempName = tempDir.createRandomScriptFileName(problemId+"-solution");
        String response = runCode(solution, contestProblem.getCorrectSolutionLanguage(), tempName, modelCreateTestCase.getTestCase(), contestProblem.getTimeLimit(), "Language Not Found");
        TestCase testCase = TestCase.builder()
                .contestProblem(contestProblem)
                .testCase(modelCreateTestCase.getTestCase())
                .testCasePoint(modelCreateTestCase.getTestCasePoint())
                .correctAnswer(response)
                .build();
        testCaseRepo.save(testCase);
        return testCase;
    }

    @Override
    public TestCase updateTestCase(ModelCreateTestCase modelCreateTestCase, UUID testCaseId) throws Exception {
        TestCase testCase = testCaseRepo.findTestCaseByTestCaseId(testCaseId);
        if(testCase == null){
            throw new Exception("testcase not found");
        }
        ContestProblem contestProblem = testCase.getContestProblem();
        String solution = contestProblem.getCorrectSolutionSourceCode();
        String tempName = tempDir.createRandomScriptFileName(contestProblem.getProblemId()+"-solution");
        String response = runCode(solution, contestProblem.getCorrectSolutionLanguage(), tempName, modelCreateTestCase.getTestCase(), contestProblem.getTimeLimit(), "Language Not Found");
        testCase.setTestCase(modelCreateTestCase.getTestCase());
        testCase.setCorrectAnswer(response);
        return testCase;
    }

    @Override
    public Page<ContestProblem> getContestProblemPaging(Pageable pageable) {
        return contestProblemPagingAndSortingRepo.findAll(pageable);
    }

    @Override
    public ContestProblem findContestProblemByProblemId(String problemId) throws Exception {
        try {
            ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
            return contestProblem;
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }

    @Override
    public void saveTestCase(TestCase testCase) throws Exception {
        try {
            testCaseRepo.save(testCase);
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }

    @Override
    public String executableIDECode(ModelRunCodeFromIDE modelRunCodeFromIDE, String userName, String computerLanguage) throws Exception {
        String tempName = tempDir.createRandomScriptFileName(userName + "-" + computerLanguage);
        String response = runCode(modelRunCodeFromIDE.getSource(), computerLanguage, tempName, modelRunCodeFromIDE.getInput(), 10, "Language Not Found");
        tempDir.pushToConcurrentLinkedQueue(tempName);
        return response;
    }

    @Override
    public ContestProblem getContestProblem(String problemId) throws Exception {
        ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
        if(contestProblem == null){
            throw new Exception("Problem not found");
        }
        return contestProblem;
    }



    @Override
    public ModelProblemDetailRnCodeResponse problemDetailRunCode(String problemId, ModelProblemDetailRunCode modelProblemDetailRunCode, String userName) throws Exception {
        ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
        String tempName = tempDir.createRandomScriptFileName(contestProblem.getProblemName() + "-" + contestProblem.getCorrectSolutionLanguage());
        String expected = runCode(contestProblem.getCorrectSolutionSourceCode(), contestProblem.getCorrectSolutionLanguage(), tempName, modelProblemDetailRunCode.getInput(), contestProblem.getTimeLimit(), "Correct Solution Language Not Found");
        String output = runCode(modelProblemDetailRunCode.getSourceCode(), modelProblemDetailRunCode.getComputerLanguage(), tempName+"-"+userName, modelProblemDetailRunCode.getInput(), contestProblem.getTimeLimit(), "User Source Code Langua Not Found");
        return ModelProblemDetailRnCodeResponse.builder().build();
    }

    private String runCode(String sourceCode, String computerLanguage, String tempName, String input, int timeLimit, String exception) throws Exception {
        String ans;
        switch (computerLanguage){
            case "CPP":
                tempDir.createScriptFile(sourceCode, input, timeLimit, ComputerLanguage.Languages.CPP, tempName);
                ans = dockerClientBase.runExecutable(ComputerLanguage.Languages.CPP,  tempName);
                break;
            case "JAVA":
                tempDir.createScriptFile(sourceCode, input, timeLimit, ComputerLanguage.Languages.JAVA, tempName);
                ans = dockerClientBase.runExecutable(ComputerLanguage.Languages.JAVA, tempName);
                break;
            case "PYTHON3":
                tempDir.createScriptFile(sourceCode, input, timeLimit, ComputerLanguage.Languages.PYTHON3, tempName);
                ans = dockerClientBase.runExecutable(ComputerLanguage.Languages.PYTHON3, tempName);
                break;
            case "GOLANG":
                tempDir.createScriptFile(sourceCode, input, timeLimit, ComputerLanguage.Languages.GOLANG, tempName);
                ans = dockerClientBase.runExecutable(ComputerLanguage.Languages.GOLANG, tempName);
                break;
            default:
                throw new Exception(exception);
        }
        tempDir.pushToConcurrentLinkedQueue(tempName);
        return ans;
    }
}
