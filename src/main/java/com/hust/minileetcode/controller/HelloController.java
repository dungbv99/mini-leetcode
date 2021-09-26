package com.hust.minileetcode.controller;

import com.hust.minileetcode.docker.DockerClientBase;
import com.hust.minileetcode.utils.ComputerLanguage;
import com.hust.minileetcode.utils.TempDir;
import com.spotify.docker.client.exceptions.DockerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {
    private DockerClientBase dockerClientBase = new DockerClientBase();
    private TempDir tempDir = new TempDir();

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/test1")
    public String index() {
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

        try {
            tempDir.createScriptFile(source, ComputerLanguage.Languages.CPP, tempName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = new String("err");
        try {
            response = dockerClientBase.runExecutable(ComputerLanguage.Languages.CPP,  tempName);
//            tempDir.removeDir(tempName);
        } catch (DockerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}