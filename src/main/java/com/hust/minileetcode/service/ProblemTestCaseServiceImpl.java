package com.hust.minileetcode.service;

import com.hust.minileetcode.docker.DockerClientBase;
import com.hust.minileetcode.entity.*;
import com.hust.minileetcode.exception.MiniLeetCodeException;
import com.hust.minileetcode.model.*;
import com.hust.minileetcode.repo.*;
import com.hust.minileetcode.rest.entity.UserLogin;
import com.hust.minileetcode.rest.repo.UserLoginRepo;
import com.hust.minileetcode.utils.ComputerLanguage;
import com.hust.minileetcode.utils.TempDir;
import com.hust.minileetcode.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProblemTestCaseServiceImpl implements ProblemTestCaseService {
    private ProblemRepo problemRepo;
    private TestCaseRepo testCaseRepo;
    private ProblemSourceCodeRepo problemSourceCodeRepo;
    private DockerClientBase dockerClientBase;
    private TempDir tempDir;
    private ProblemPagingAndSortingRepo problemPagingAndSortingRepo;
    private ProblemSubmissionRepo problemSubmissionRepo;
    private UserLoginRepo userLoginRepo;
    private ContestRepo contestRepo;
    private Constants  constants;
    private ContestPagingAndSortingRepo contestPagingAndSortingRepo;
    private UserSubmissionResultRepo userSubmissionResultRepo;
    private ContestSubmissionRepo contestSubmissionRepo;
    private UserRegistrationContestRepo userRegistrationContestRepo;
    @Override
    public void createContestProblem(ModelCreateContestProblem modelCreateContestProblem) throws Exception {
        if(problemRepo.findByProblemId(modelCreateContestProblem.getProblemId()) != null){
            throw new MiniLeetCodeException("problem id already exist");
        }
        try {
            Problem problem = Problem.builder()
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
                    .createdAt(new Date())
                    .levelOrder(constants.getMapLevelOrder().get(modelCreateContestProblem.getLevelId()))
//                .testCases(null)
                    .build();
            problemRepo.save(problem);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }


    }

    @Override
    public Problem updateContestProblem(ModelCreateContestProblem modelCreateContestProblem, String problemId) throws Exception {

        if(!problemRepo.existsById(problemId)){
            throw new MiniLeetCodeException("problem id not found");
        }
        Problem problem = problemRepo.findByProblemId(problemId);
        problem.setProblemName(modelCreateContestProblem.getProblemName());
        problem.setProblemDescription(modelCreateContestProblem.getProblemDescription());
        problem.setLevelId(modelCreateContestProblem.getLevelId());
        problem.setCategoryId(modelCreateContestProblem.getCategoryId());
        problem.setSolution(modelCreateContestProblem.getSolution());
        problem.setTimeLimit(modelCreateContestProblem.getTimeLimit());
        problem.setCorrectSolutionLanguage(modelCreateContestProblem.getCorrectSolutionLanguage());
        problem.setCorrectSolutionSourceCode(modelCreateContestProblem.getCorrectSolutionSourceCode());
        try {
            return problemRepo.save(problem);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    @Override
    public void updateProblemSourceCode(ModelAddProblemLanguageSourceCode modelAddProblemLanguageSourceCode, String problemId) {
//        ProblemSourceCode problemSourceCode = new ProblemSourceCode();
//        problemSourceCode.setProblemSourceCodeId(modelAddProblemLanguageSourceCode.getProblemSourceCodeId());
//        problemSourceCode.setMainSource(modelAddProblemLanguageSourceCode.getMainSource());
//        problemSourceCode.setBaseSource(modelAddProblemLanguageSourceCode.getBaseSource());
//        problemSourceCode.setLanguage(modelAddProblemLanguageSourceCode.getLanguage());
//        problemSourceCode.setProblemFunctionDefaultSource(modelAddProblemLanguageSourceCode.getProblemFunctionDefaultSource());
//        problemSourceCode.setProblemFunctionSolution(modelAddProblemLanguageSourceCode.getProblemFunctionSolution());
//        ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
//        if(contestProblem.getProblemSourceCode() == null){
//            ArrayList<ProblemSourceCode> problemSourceCodes = new ArrayList<ProblemSourceCode>();
//            problemSourceCodes.add(problemSourceCode);
//            contestProblem.setProblemSourceCode(problemSourceCodes);
//        }else{
//            contestProblem.getProblemSourceCode().add(problemSourceCode);
//        }
//        problemSourceCodeRepo.save(problemSourceCode);
//        contestProblemRepo.save(contestProblem);
    }

    @Override
    public TestCase createTestCase(ModelCreateTestCase modelCreateTestCase, String problemId) throws Exception {
        try {
            Problem problem = problemRepo.findByProblemId(problemId);
            String solution = problem.getCorrectSolutionSourceCode();
            String tempName = tempDir.createRandomScriptFileName(problemId+"-solution");
            String response = runCode(solution, problem.getCorrectSolutionLanguage(), tempName, modelCreateTestCase.getTestCase(), problem.getTimeLimit(), "Language Not Found");
            TestCase testCase = TestCase.builder()
                    .problem(problem)
                    .testCase(modelCreateTestCase.getTestCase())
                    .testCasePoint(modelCreateTestCase.getTestCasePoint())
                    .correctAnswer(response)
                    .build();
            testCaseRepo.save(testCase);
            return testCase;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public TestCase updateTestCase(ModelCreateTestCase modelCreateTestCase, UUID testCaseId) throws Exception {
        try {
            TestCase testCase = testCaseRepo.findTestCaseByTestCaseId(testCaseId);
            if(testCase == null){
                throw new Exception("testcase not found");
            }
            Problem problem = testCase.getProblem();
            String solution = problem.getCorrectSolutionSourceCode();
            String tempName = tempDir.createRandomScriptFileName(problem.getProblemId()+"-solution");
            String response = runCode(solution, problem.getCorrectSolutionLanguage(), tempName, modelCreateTestCase.getTestCase(), problem.getTimeLimit(), "Language Not Found");
            testCase.setTestCase(modelCreateTestCase.getTestCase());
            testCase.setCorrectAnswer(response);
            return testCase;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<Problem> getContestProblemPaging(Pageable pageable) throws Exception {
        try {
            return problemPagingAndSortingRepo.findAll(pageable);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public Problem findContestProblemByProblemId(String problemId) throws Exception {
        try {
            return problemRepo.findByProblemId(problemId);
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
    public Problem getContestProblem(String problemId) throws Exception {
        Problem problem;
        try {
            problem = problemRepo.findByProblemId(problemId);
            if(problem == null){
                throw new MiniLeetCodeException("Problem not found");
            }
            return problem;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    @Override
    public ModelProblemDetailRunCodeResponse problemDetailRunCode(String problemId, ModelProblemDetailRunCode modelProblemDetailRunCode, String userName) throws Exception {
        Problem problem = problemRepo.findByProblemId(problemId);
        String tempName = tempDir.createRandomScriptFileName(problem.getProblemName() + "-" + problem.getCorrectSolutionLanguage());
        String output = runCode(modelProblemDetailRunCode.getSourceCode(),
                modelProblemDetailRunCode.getComputerLanguage(),
                tempName+"-"+userName+"-code",
                modelProblemDetailRunCode.getInput(),
                problem.getTimeLimit(),
                "User Source Code Langua Not Found");

        output = output.substring(0, output.length()-1);

        int lastLineIndexOutput = output.lastIndexOf("\n");
        if(output.equals("Time Limit Exceeded")){
            return ModelProblemDetailRunCodeResponse.builder()
                    .status("Time Limit Exceeded")
                    .build();
        }
        String status = output.substring(lastLineIndexOutput);
        log.info("status {}", status);
        if(status.contains("Compile Error")){
            return ModelProblemDetailRunCodeResponse.builder()
                    .output(output.substring(0, lastLineIndexOutput))
                    .status("Compile Error")
                    .build();
        }
        log.info("status {}", status);
        output = output.substring(0, lastLineIndexOutput);
        String expected = runCode(problem.getCorrectSolutionSourceCode(),
                problem.getCorrectSolutionLanguage(),
                tempName+"-solution",
                modelProblemDetailRunCode.getInput(),
                problem.getTimeLimit(),
                "Correct Solution Language Not Found");
        expected = expected.substring(0, expected.length()-1);
        int lastLinetIndexExpected = expected.lastIndexOf("\n");
        expected = expected.substring(0, lastLinetIndexExpected);
        expected = expected.replaceAll("\n", "");
        output = output.replaceAll("\n", "");
        if(output.equals(expected)){
            status = "Accept";
        }else{
            status = "Wrong Answer";
        }
        log.info("output {}", output);
        log.info("expected {}", expected);
        return ModelProblemDetailRunCodeResponse.builder()
                .expected(expected)
                .output(output)
                .status(status)
                .build();
    }

    @Override
    public String getTestCaseResult(String problemId, String userName, ModelGetTestCaseResult modelGetTestCaseResult) throws Exception {
        Problem problem = problemRepo.findByProblemId(problemId);
        String tempName = tempDir.createRandomScriptFileName(userName + "-" + problem.getProblemName() + "-" + problem.getCorrectSolutionLanguage());
        String output = runCode(problem.getCorrectSolutionSourceCode(), problem.getCorrectSolutionLanguage(), tempName, modelGetTestCaseResult.getTestcase(), problem.getTimeLimit(), "Correct Solution Language Not Found");
        output = output.substring(0, output.length()-1);
        int lastLinetIndexExpected = output.lastIndexOf("\n");
        output = output.substring(0, lastLinetIndexExpected);
        output = output.replaceAll("\n", "");
        log.info("output {}", output);
        return output;
    }

    @Override
    public String checkCompile(ModelCheckCompile modelCheckCompile, String userName) throws Exception {
        String tempName = tempDir.createRandomScriptFileName(userName);
        String resp;
        switch (modelCheckCompile.getComputerLanguage()){
            case "CPP":
                tempDir.createScriptCompileFile(modelCheckCompile.getSource(), ComputerLanguage.Languages.CPP, tempName);
                resp = dockerClientBase.runExecutable(ComputerLanguage.Languages.CPP, tempName);
                break;
            case "JAVA":
                tempDir.createScriptCompileFile(modelCheckCompile.getSource(), ComputerLanguage.Languages.JAVA, tempName);
                resp = dockerClientBase.runExecutable(ComputerLanguage.Languages.JAVA, tempName);
                break;
            case "PYTHON3":
                tempDir.createScriptCompileFile(modelCheckCompile.getSource(), ComputerLanguage.Languages.PYTHON3, tempName);
                resp = dockerClientBase.runExecutable(ComputerLanguage.Languages.PYTHON3, tempName);
                break;
            case "GOLANG":
                tempDir.createScriptCompileFile(modelCheckCompile.getSource(), ComputerLanguage.Languages.GOLANG, tempName);
                resp = dockerClientBase.runExecutable(ComputerLanguage.Languages.GOLANG, tempName);
                break;
            default:
                throw new Exception("Language not found");
        }
        if(resp.contains("Successful")){
            return "Successful";
        }else{
            return "Compile Error";
        }
    }

    @Override
    public TestCase saveTestCase(String problemId, ModelSaveTestcase modelSaveTestcase) {

        Problem problem = problemRepo.findByProblemId(problemId);
        TestCase testCase = TestCase.builder()
                .correctAnswer(modelSaveTestcase.getResult())
                .testCase(modelSaveTestcase.getInput())
                .problem(problem)
                .build();
        return testCaseRepo.save(testCase);
    }


    @Override
    public ListProblemSubmissionResponse getListProblemSubmissionResponse(String problemId, String userId) throws Exception {
        UserLogin userLogin = userLoginRepo.findByUserLoginId(userId);
        Problem problem = problemRepo.findByProblemId(problemId);
        if(userLogin == null || problem == null){
            throw new Exception("not found");
        }
        List<Object[]> list = problemSubmissionRepo.getListProblemSubmissionByUserAndProblemId(userLogin, problem);
        List<ProblemSubmissionResponse> problemSubmissionResponseList = new ArrayList<>();
        try {
            list.forEach(objects -> {
                log.info("objects {}", objects);
                ProblemSubmissionResponse problemSubmissionResponse = ProblemSubmissionResponse.builder()
                        .problemSubmissionId((UUID) objects[0])
                        .timeSubmitted((String) objects[1])
                        .status((String) objects[2])
                        .score((int) objects[3])
                        .runtime((String) objects[4])
                        .memoryUsage((float) objects[5])
                        .language((String) objects[6])
                        .build();
                problemSubmissionResponseList.add(problemSubmissionResponse);
            });
        } catch (Exception e){
            log.info("error");
            throw e;
        }

        return ListProblemSubmissionResponse.builder()
                .contents(problemSubmissionResponseList)
                .isSubmitted(list.size() != 0)
                .build();
    }

    @Override
    public Contest createContest(ModelCreateContest modelCreateContest, String userName) throws Exception {
        try {
            Contest contestExist = contestRepo.findContestByContestId(modelCreateContest.getContestId());
            if(contestExist != null){
                throw new MiniLeetCodeException("Contest is already exist");
            }
            UserLogin userLogin = userLoginRepo.findByUserLoginId(userName);
            List<Problem> problems = getContestProblemsFromListContestId(modelCreateContest.getProblemIds());
            Contest contest = Contest.builder()
                    .contestId(modelCreateContest.getContestId())
                    .contestName(modelCreateContest.getContestName())
                    .contestSolvingTime(modelCreateContest.getContestTime())
                    .problems(problems)
                    .userLogin(userLogin)
                    .build();
            return contestRepo.save(contest);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Contest updateContest(ModelUpdateContest modelUpdateContest, String userName, String contestId) throws Exception {
        try {
            Contest contestExist = contestRepo.findContestByContestId(contestId);
            if(contestExist == null){
                throw new MiniLeetCodeException("Contest does not exist");
            }
            UserLogin userLogin = userLoginRepo.findByUserLoginId(userName);
            //check user have privileged
            if(!userLogin.getUserLoginId().equals(contestExist.getUserLogin().getUserLoginId())){
                throw new MiniLeetCodeException("You don't have privileged");
            }
            List<Problem> problems = getContestProblemsFromListContestId(modelUpdateContest.getProblemIds());
            Contest contest = Contest.builder()
                    .contestName(modelUpdateContest.getContestName())
                    .contestSolvingTime(modelUpdateContest.getContestSolvingTime())
                    .problems(problems)
                    .userLogin(userLogin)
                    .build();
            return contestRepo.save(contest);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ModelProblemSubmissionDetailResponse findProblemSubmissionById(UUID id, String userName) throws MiniLeetCodeException {
        ProblemSubmission problemSubmission = problemSubmissionRepo.findByProblemSubmissionId(id);
        if (!problemSubmission.getUserLogin().getUserLoginId().equals(userName)){
            throw new MiniLeetCodeException("unauthor");
        }
        return ModelProblemSubmissionDetailResponse.builder()
                .problemSubmissionId(problemSubmission.getProblemSubmissionId())
                .problemId(problemSubmission.getProblem().getProblemId())
                .problemName(problemSubmission.getProblem().getProblemName())
                .submittedAt(problemSubmission.getTimeSubmitted())
                .submissionSource(problemSubmission.getSourceCode())
                .submissionLanguage(problemSubmission.getSourceCodeLanguages())
                .score(problemSubmission.getScore())
                .testCasePass(problemSubmission.getTestCasePass())
                .runTime(problemSubmission.getRuntime())
                .memoryUsage(problemSubmission.getMemoryUsage())
                .status(problemSubmission.getStatus())
                .build();
    }

    @Override
    public ModelGetContestPageResponse getContestPaging(Pageable pageable) {
        Page<Contest> contestPage =  contestPagingAndSortingRepo.findAll(pageable);
        List<ModelGetContestResponse> lists = new ArrayList<>();
        contestPage.forEach(contest -> {
            ModelGetContestResponse modelGetContestResponse = ModelGetContestResponse.builder()
                    .contestId(contest.getContestId())
                    .contestName(contest.getContestName())
                    .contestTime(contest.getContestSolvingTime())
                    .build();
            lists.add(modelGetContestResponse);
        });
        return ModelGetContestPageResponse.builder()
                .contents(lists)
                .build();
    }

    @Override
    public ModelGetContestDetailResponse getContestDetailByContestId(String contestId) {
        Contest contest = contestRepo.findContestByContestId(contestId);
        List<ModelGetProblemDetailResponse> problems = new ArrayList<>();
        contest.getProblems().forEach(contestProblem -> {
            ModelGetProblemDetailResponse p = ModelGetProblemDetailResponse.builder()
                    .levelId(contestProblem.getLevelId())
                    .problemId(contestProblem.getProblemId())
                    .problemName(contestProblem.getProblemName())
                    .levelOrder(contestProblem.getLevelOrder())
                    .problemDescription(contestProblem.getProblemDescription())
                    .build();
            problems.add(p);
        });
        return ModelGetContestDetailResponse.builder()
                .contestId(contestId)
                .contestName(contest.getContestName())
                .contestTime(contest.getContestSolvingTime())
                .list(problems)
                .build();
    }

    @Override
    public ModelProblemSubmissionResponse problemDetailSubmission(ModelProblemDetailSubmission modelProblemDetailSubmission, String problemId, String userName) throws Exception {
        log.info("source {} ", modelProblemDetailSubmission.getSource());
        UserLogin userLogin = userLoginRepo.findByUserLoginId(userName);
        if(userLogin  == null){
            throw new Exception(("user not found"));
        }
        Problem problem = problemRepo.findByProblemId(problemId);
        if(problem == null){
            throw new Exception("Contest problem does not exist");
        }
        List<TestCase> testCaseList = testCaseRepo.findAllByProblem(problem);
        if (testCaseList == null){
            throw new Exception("Problem Does not have testcase");
        }
        String tempName = tempDir.createRandomScriptFileName(userName+"-"+problemId);
        String response = submission(modelProblemDetailSubmission.getSource(), modelProblemDetailSubmission.getLanguage(), tempName, testCaseList,"Language Not Found", problem.getTimeLimit());
        log.info("response {}", response);
        response = response.substring(0, response.length()-1);
        int lastIndex = response.lastIndexOf("\n");
        String status = response.substring(lastIndex);
        log.info("status {}", status);
        if(status.contains("Compile Error")){
            log.info("return problem submission compile error");
            ProblemSubmission problemSubmission = ProblemSubmission.builder()
                    .score(0)
                    .userLogin(userLogin)
                    .problem(problem)
                    .sourceCode(modelProblemDetailSubmission.getSource())
                    .status(status)
                    .testCasePass(0+"/"+testCaseList.size())
                    .sourceCodeLanguages(modelProblemDetailSubmission.getLanguage())
                    .build();
            ProblemSubmission p = problemSubmissionRepo.save(problemSubmission);
            return ModelProblemSubmissionResponse.builder()
                    .status(status)
                    .result(p.getTestCasePass())
                    .runtime(p.getRuntime())
                    .memoryUsage(p.getMemoryUsage())
                    .language(p.getSourceCodeLanguages())
                    .problemName(problem.getProblemName())
                    .problemSubmissionId(p.getProblemSubmissionId())
                    .build();
        }
        String []ans = response.split("testcasedone\n");
        status = null;
        int cnt = 0;
        int score = 0;
        for(int i = 0; i < testCaseList.size(); i++){
            if(!testCaseList.get(i).getCorrectAnswer().equals(ans[i])){
                if(status == null && ans[i].contains("Time Limit Exceeded")){
                    status = "Time Limit Exceeded";
                }else{
                    status = "Wrong Answer";
                }
            }else{
                score = testCaseList.get(i).getTestCasePoint();
                cnt++;
            }
        }
        if(status == null){
            status = "Accept";
        }
        log.info("pass {}/{}", cnt, testCaseList.size());
        ProblemSubmission problemSubmission = ProblemSubmission.builder()
                .score(score)
                .userLogin(userLogin)
                .problem(problem)
                .sourceCode(modelProblemDetailSubmission.getSource())
                .status(status)
                .testCasePass(cnt+"/"+testCaseList.size())
                .sourceCodeLanguages(modelProblemDetailSubmission.getLanguage())
                .build();
        ProblemSubmission problemSubmission1 = problemSubmissionRepo.save(problemSubmission);
        return ModelProblemSubmissionResponse.builder()
                .status(status)
                .result(cnt+"/"+testCaseList.size())
                .problemSubmissionId(problemSubmission1.getProblemSubmissionId())
                .language(modelProblemDetailSubmission.getLanguage())
                .score(score)
                .memoryUsage(problemSubmission1.getMemoryUsage())
                .runtime(problemSubmission1.getRuntime())
                .timeSubmitted(problemSubmission1.getTimeSubmitted())
                .problemName(problem.getProblemName())
                .problemSubmissionId(problemSubmission1.getProblemSubmissionId())
                .build();
    }

    @Override
    public ModelContestSubmissionResponse submitContestProblem(ModelContestSubmission modelContestSubmission, String userName) throws Exception {
        Problem problem = problemRepo.findByProblemId(modelContestSubmission.getProblemId());
        UserLogin userLogin = userLoginRepo.findByUserLoginId(userName);
        Contest contest = contestRepo.findContestByContestId(modelContestSubmission.getContestId());
        List<TestCase> testCaseList = testCaseRepo.findAllByProblem(problem);
        String tempName = tempDir.createRandomScriptFileName(userName+"-"+modelContestSubmission.getContestId()+"-"+modelContestSubmission.getProblemId());
        String response = submission(modelContestSubmission.getSource(), modelContestSubmission.getLanguage(), tempName, testCaseList, "language not found", problem.getTimeLimit());
        log.info("response {}", response);
        response = response.substring(0, response.length()-1);
        int lastIndex = response.lastIndexOf("\n");
        String status = response.substring(lastIndex);
        log.info("status {}", status);
        if(status.contains("Compile Error")) {
            log.info("return problem submission compile error");
            ProblemSubmission problemSubmission = ProblemSubmission.builder()
                    .score(0)
                    .userLogin(userLogin)
                    .problem(problem)
                    .sourceCode(modelContestSubmission.getSource())
                    .status(status)
                    .testCasePass(0+"/"+testCaseList.size())
                    .sourceCodeLanguages(modelContestSubmission.getLanguage())
                    .build();
            ProblemSubmission p = problemSubmissionRepo.save(problemSubmission);
            ContestSubmission contestSubmission = ContestSubmission.builder()
                    .contest(contest)
                    .status("Compile Error")
                    .point(0)
                    .contest(contest)
                    .problem(problem)
                    .userLogin(userLogin)
                    .problemSubmission(p)
                    .build();
            contestSubmission = contestSubmissionRepo.save(contestSubmission);
            return ModelContestSubmissionResponse.builder()
                    .status(status)
                    .testCasePass(p.getTestCasePass())
                    .runtime(p.getRuntime())
                    .memoryUsage(p.getMemoryUsage())
                    .problemName(problem.getProblemName())
                    .contestSubmissionID(contestSubmission.getContestSubmissionId())
                    .build();
        }
        String []ans = response.split("testcasedone\n");
        status = null;
        int cnt = 0;
        int score = 0;
        for(int i = 0; i < testCaseList.size(); i++){
            if(!testCaseList.get(i).getCorrectAnswer().equals(ans[i])){
                if(status == null && ans[i].contains("Time Limit Exceeded")){
                    status = "Time Limit Exceeded";
                }else{
                    status = "Wrong Answer";
                }
            }else{
                score = testCaseList.get(i).getTestCasePoint();
                cnt++;
            }
        }
        if(status == null){
            status = "Accept";
        }
        log.info("pass {}/{}", cnt, testCaseList.size());
        ProblemSubmission problemSubmission = ProblemSubmission.builder()
                .score(score)
                .userLogin(userLogin)
                .problem(problem)
                .sourceCode(modelContestSubmission.getSource())
                .status(status)
                .testCasePass(cnt+"/"+testCaseList.size())
                .sourceCodeLanguages(modelContestSubmission.getLanguage())
                .build();
        ProblemSubmission p = problemSubmissionRepo.save(problemSubmission);

        ContestSubmission contestSubmission = ContestSubmission.builder()
                .contest(contest)
                .status(status)
                .point(0)
                .contest(contest)
                .problem(problem)
                .userLogin(userLogin)
                .problemSubmission(p)
                .build();
        contestSubmission = contestSubmissionRepo.save(contestSubmission);
        return ModelContestSubmissionResponse.builder()
                .status(status)
                .testCasePass(p.getTestCasePass())
                .runtime(p.getRuntime())
                .memoryUsage(p.getMemoryUsage())
                .problemName(problem.getProblemName())
                .contestSubmissionID(contestSubmission.getContestSubmissionId())
                .build();
    }

    @Override
    public ModelStudentRegisterCourseResponse studentRegisterContest(String contestId, String userId) {
        Contest contest = contestRepo.findContestByContestId(contestId);
        UserLogin userLogin = userLoginRepo.findByUserLoginId(userId);
        UserRegistrationContest userRegistrationContest = UserRegistrationContest.builder()
                .contest(contest)
                .userLogin(userLogin)
                .status(Constants.RegistrationType.PENDING.getValue())
                .build();
        userRegistrationContestRepo.save(userRegistrationContest);

        return ModelStudentRegisterCourseResponse.builder()
                .status(Constants.RegistrationType.PENDING.getValue())
                .message("You have send request to register contest "+ contestId +", please wait to accept")
                .build();
    }

    private List<Problem> getContestProblemsFromListContestId(List<String> problemIds) throws MiniLeetCodeException {
        List<Problem> problems = new ArrayList<>();
        for(String problemId : problemIds){
            Problem problem = problemRepo.findByProblemId(problemId);
            if(problem == null){
                throw new MiniLeetCodeException("Problem " + problemId +" does not exist");
            }
            problems.add(problem);
        }
        return problems;
    }


    private String submission(String source, String computerLanguage, String tempName, List<TestCase> testCaseList, String exception, int timeLimit) throws Exception {
        String ans;
        tempName = tempName.replaceAll(" ","");
        switch (computerLanguage){
            case "CPP":
                tempDir.createScriptSubmissionFile(ComputerLanguage.Languages.CPP, tempName, testCaseList, source, timeLimit);
                ans = dockerClientBase.runExecutable(ComputerLanguage.Languages.CPP,  tempName);
                break;
            case "JAVA":
                tempDir.createScriptSubmissionFile(ComputerLanguage.Languages.JAVA, tempName, testCaseList, source, timeLimit);
                ans = dockerClientBase.runExecutable(ComputerLanguage.Languages.JAVA, tempName);
                break;
            case "PYTHON3":
                tempDir.createScriptSubmissionFile(ComputerLanguage.Languages.PYTHON3, tempName, testCaseList, source, timeLimit);
                ans = dockerClientBase.runExecutable(ComputerLanguage.Languages.PYTHON3, tempName);
                break;
            case "GOLANG":
                tempDir.createScriptSubmissionFile(ComputerLanguage.Languages.GOLANG, tempName, testCaseList, source, timeLimit);
                ans = dockerClientBase.runExecutable(ComputerLanguage.Languages.GOLANG, tempName);
                break;
            default:
                throw new Exception(exception);
        }
//        tempDir.pushToConcurrentLinkedQueue(tempName);
        return ans;
    }

    private String runCode(String sourceCode, String computerLanguage, String tempName, String input, int timeLimit, String exception) throws Exception {
        String ans;
        tempName = tempName.replaceAll(" ","");
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
