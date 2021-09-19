package com.hust.minileetcode.controller;

import com.hust.minileetcode.utils.ComputerLanguage;
import com.hust.minileetcode.utils.TempDir;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {

    private TempDir tempDir = new TempDir();

    @GetMapping("/hello")
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

        String fileName = tempDir.createRandomScriptFileName("test");

        try {
            tempDir.createScriptFile(source, ComputerLanguage.Languages.CPP, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "Greetings from Spring Boot!";
    }

}