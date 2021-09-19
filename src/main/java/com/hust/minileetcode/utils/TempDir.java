package com.hust.minileetcode.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PrimitiveIterator.OfInt;
import java.util.Random;


@Configuration
public class TempDir {

    private static final String TEMPDIR = "./temp_dir/";

    private static final int RANDOM_LENGTH = 5;

    private static final int maxVal = 99999;

    private static final int minVal = 10000;

    private static final String SHFileStart = "#!/bin/bash\n";

    private OfInt r = new Random().ints(minVal, maxVal).iterator();



    @Bean
    public void initTmepDir(){
        File theDir = new File(TEMPDIR);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }

    public String createRandomScriptFileName(String startName){
        int generateRandom = r.nextInt();
        return startName + "-" + generateRandom;
    }

    public void createScriptFile(String source, ComputerLanguage.Languages languages, String fileName ) throws IOException {
        String suffixes = "";
        String cmd = "";
        switch (languages){
            case CPP:
                suffixes = ".cpp";
                cmd = "g++ -o main main.cpp" + "\n" +"./main" +"\n";
                break;
            case JAVA:
                suffixes = ".java";
                break;
            case PYTHON3:
                suffixes = ".py";
                break;
            case GOLANG:
                suffixes = ".go";
                break;
            default:
                suffixes = "";
                break;
        }

        String sourceSh = SHFileStart
                + "cat <<EOF >> main"  + suffixes + "\n"
                + source + "\n"
                + "EOF" + "\n"
                + cmd + "\n";

        BufferedWriter writer = new BufferedWriter(new FileWriter(TEMPDIR+fileName+".sh"));
        writer.write(sourceSh);
        writer.close();
    }

}
