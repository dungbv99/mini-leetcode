package com.hust.minileetcode.controller;

import com.hust.minileetcode.docker.DockerClientBase;
import com.hust.minileetcode.entity.ContestProblem;
import com.hust.minileetcode.entity.TestCase;
import com.hust.minileetcode.exception.MiniLeetCodeException;
import com.hust.minileetcode.repo.ContestProblemPagingAndSortingRepo;
import com.hust.minileetcode.repo.ContestProblemRepo;
import com.hust.minileetcode.repo.TestCaseRepo;
import com.hust.minileetcode.utils.ComputerLanguage;
import com.hust.minileetcode.utils.TempDir;
import com.spotify.docker.client.exceptions.DockerException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HelloController {
    private ContestProblemPagingAndSortingRepo contestProblemPagingAndSortingRepo;
    private ContestProblemRepo contestProblemRepo;
    private DockerClientBase dockerClientBase = new DockerClientBase();
    private TempDir tempDir = new TempDir();
    private TestCaseRepo testCaseRepo;
    private static final String buildCmd = "g++ -o main main.cpp";
    private static final String suffixes =".cpp";
    private static final String SHFileStart = "#!/bin/bash\n";
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/test1")
    public String index() throws DockerException, IOException, InterruptedException {
        String source = "// vector::at\n" +
                "#include <iostream>\n" +
                "#include <vector>\n" +
                "\n" +
                "int main ()\n" +
                "{\n" +
                "  std::vector<int> myvector (10);   // 10 zero-initialized ints\n" +
                "\n" +
                "  // assign some values:\n" +
                "  for (unsigned i=0; i<myvector.size(); i++)\n" +
                "    myvector.at(i)=i;\n" +
                "\n" +
                "  std::cout << \"myvector contains:\";\n" +
                "  for (unsigned i=0; i<myvector.size(); i++)\n" +
                "    std::cout << ' ' << myvector.at(i);\n" +
                "  std::cout << '\\n';\n" +
                "\n" +
                "  return 0;\n" +
                "}" ;

        String tempName = tempDir.createRandomScriptFileName("test");
        String testCase = "";
        int timeLimit = 1;
        try {
            tempDir.createScriptFile(source, testCase, timeLimit, ComputerLanguage.Languages.CPP, tempName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = new String("err");
        response = dockerClientBase.runExecutable(ComputerLanguage.Languages.CPP,  tempName);
        tempDir.removeDir(tempName);

        return response;
    }

    @GetMapping("/test")
    public String test() throws IOException {
        String problemId = "1.Add 2 Number";
        ContestProblem contestProblem = contestProblemRepo.findByProblemId(problemId);
        log.info("contestProblem {}", contestProblem);
        List<TestCase> testCases = testCaseRepo.findAllByContestProblem(contestProblem);
        log.info("testcase size {}", testCases.size());
        String source = genSubmitScriptFile(testCases, contestProblem.getCorrectSolutionSourceCode(), "test", 1);
        BufferedWriter writer = new BufferedWriter(new FileWriter("./temp_dir/" + "a.sh"));
        writer.write(source);
        writer.close();
        return "ok";
    }
    public String genSubmitScriptFile(List<TestCase> testCases, String source, String tmpName, int timeout){
        String genTestCase = "";
        for(int i = 0; i < testCases.size(); i++){
            String testcase =
                    "cat <<EOF >> testcase" + i + ".txt \n"
                    + testCases.get(i).getTestCase() +"\n"
                    +"EOF" + "\n";
            genTestCase += testcase;
        }
        String sourceSH = SHFileStart
                + "mkdir -p " + tmpName +"\n"
                + "cd " + tmpName +"\n"
                + "cat <<EOF >> main"  + suffixes + "\n"
                + source + "\n"
                + "EOF" + "\n"
                + buildCmd +"\n"
                + "FILE=main" +"\n"
                +"if test -f \"$FILE\"; then" +"\n"
                + genTestCase +"\n"
                + "n=0\n"
                + "while [ \"$n\" -lt " + testCases.size()+" ]"+"\n"
                + "do\n"
                + "f=\"testcase\"$n\".txt\"" +"\n"
                + "cat $f | timeout " + timeout + "s" +" ./main" +"\n"
                + "n=`expr $n + 1`\n"
                + "done\n"
                + "else\n"
                + "echo Compile Error\n"
                + "fi" + "\n"
                + "cd .. \n"
                + "rm -rf " + tmpName + " & "+"\n"
                + "rm -rf " + tmpName+".sh" + " & "+"\n";
        return sourceSH;
    }

    @GetMapping("/test-exception")
    public void testException() throws Exception {
        throw new Exception("err");
    }

    @GetMapping("/test-minileetcode-exception")
    public void testMiniLeetcodeException() throws MiniLeetCodeException {
        throw new MiniLeetCodeException("bad request");
    }


}