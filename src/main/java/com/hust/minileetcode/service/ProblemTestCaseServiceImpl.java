package com.hust.minileetcode.service;

import com.hust.minileetcode.docker.DockerClientBase;
import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.ProblemSourceCode;
import com.hust.minileetcode.entity.TestCase;
import com.hust.minileetcode.model.ModelAddProblemLanguageSourceCode;
import com.hust.minileetcode.model.ModelCreateContestProblem;
import com.hust.minileetcode.model.ModelCreateTestCase;
import com.hust.minileetcode.model.ModelRunCodeFromIDE;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

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
        ContestProblem contestProblem = new ContestProblem();
        if(contestProblemRepo.findByProblemId(modelCreateContestProblem.getProblemId()) != null){
            throw new Exception("problem id already exist");
        }
        contestProblem.setProblemId(modelCreateContestProblem.getProblemId());
        contestProblem.setProblemName(modelCreateContestProblem.getProblemName());
        contestProblem.setProblemDescription(modelCreateContestProblem.getProblemDescription());
        contestProblem.setCategoryId(modelCreateContestProblem.getCategoryId());
        contestProblem.setLevelId(modelCreateContestProblem.getLevelId());
        contestProblem.setMemoryLimit(modelCreateContestProblem.getMemoryLimit());
        contestProblem.setTimeLimit(modelCreateContestProblem.getTimeLimit());
        contestProblem.setProblemSourceCode(null);
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
    public String createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId) throws Exception {
        ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
        if(contestProblem.getProblemSourceCode() == null){
            throw new NullPointerException("problem does not have solution");
        }
        ProblemSourceCode problemSourceCode = contestProblem.getProblemSourceCode().get(0);
        System.out.println("problemSourceCode " +problemSourceCode.toString());
        String solution = problemSourceCode.createSolutionSourceCode();
        String tempName = tempDir.createRandomScriptFileName(problemId+"-solution");
        String response = null;
        switch (problemSourceCode.getLanguage()){
            case "CPP":
                tempDir.createScriptFile(solution, modelCreateTestCase.getTestCase(), contestProblem.getTimeLimit(), ComputerLanguage.Languages.CPP, tempName);
                response = dockerClientBase.runExecutable(ComputerLanguage.Languages.CPP,  tempName);
                break;
            case "JAVA":
                tempDir.createScriptFile(solution, modelCreateTestCase.getTestCase(), contestProblem.getTimeLimit(), ComputerLanguage.Languages.JAVA, tempName);
                response = dockerClientBase.runExecutable(ComputerLanguage.Languages.JAVA,  tempName);
                break;
            case "GOLANG":
                tempDir.createScriptFile(solution, modelCreateTestCase.getTestCase(), contestProblem.getTimeLimit(), ComputerLanguage.Languages.GOLANG, tempName);
                response = dockerClientBase.runExecutable(ComputerLanguage.Languages.GOLANG,  tempName);
                break;
            case "PYTHON3":
                tempDir.createScriptFile(solution, modelCreateTestCase.getTestCase(), contestProblem.getTimeLimit(), ComputerLanguage.Languages.PYTHON3, tempName);
                response = dockerClientBase.runExecutable(ComputerLanguage.Languages.PYTHON3,  tempName);
                break;
            default:
                throw new NotFoundException("not found language");
        }
        return response;
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
        String response = null;
        System.out.println("computerLanguage " + computerLanguage);
        switch (computerLanguage){
            case "CPP":
                tempDir.createScriptFile(modelRunCodeFromIDE.getSource(), modelRunCodeFromIDE.getInput(), 10, ComputerLanguage.Languages.CPP, tempName);
                response = dockerClientBase.runExecutable(ComputerLanguage.Languages.CPP,  tempName);
                break;
            case "JAVA":
                tempDir.createScriptFile(modelRunCodeFromIDE.getSource(), modelRunCodeFromIDE.getInput(), 10, ComputerLanguage.Languages.JAVA, tempName);
                response = dockerClientBase.runExecutable(ComputerLanguage.Languages.JAVA, tempName);
                break;
            case "PYTHON3":
                tempDir.createScriptFile(modelRunCodeFromIDE.getSource(), modelRunCodeFromIDE.getInput(), 10, ComputerLanguage.Languages.PYTHON3, tempName);
                response = dockerClientBase.runExecutable(ComputerLanguage.Languages.PYTHON3, tempName);
                break;
            case "GOLANG":
                tempDir.createScriptFile(modelRunCodeFromIDE.getSource(), modelRunCodeFromIDE.getInput(), 10, ComputerLanguage.Languages.GOLANG, tempName);
                response = dockerClientBase.runExecutable(ComputerLanguage.Languages.GOLANG, tempName);
                break;
            default:
                System.out.println("default");
                break;
        }
//        tempDir.removeDir(tempName);
//        tempDir.pushToConcurrentLinkedQueue(tempName);
        System.out.println(response);
        return response;
    }


}
