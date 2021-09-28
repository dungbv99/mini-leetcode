package com.hust.minileetcode.utils;

import com.hust.minileetcode.utils.executor.GccExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.FileSystemUtils;

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

    private GccExecutor gccExecutor = new GccExecutor();


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

    public String createDirInContainer(String startName){
        return startName+"/"+startName+".sh";
    }

    public void createScriptFile(String source, String testCase, int timeLimit, ComputerLanguage.Languages languages, String tmpName ) throws IOException {
        String suffixes = "";
        String cmd = "";
        File theDir = new File(TEMPDIR+tmpName);
        theDir.mkdirs();
        String sourceSh;
        switch (languages){
            case CPP:
                sourceSh = gccExecutor.generateScriptFileWithTestCaseAndCorrectSolution(source, testCase, tmpName, timeLimit);
                break;
//            case JAVA:
//                suffixes = ".java";
//                break;
//            case PYTHON3:
//                suffixes = ".py";
//                break;
//            case GOLANG:
//                suffixes = ".go";
//                break;
            default:
                sourceSh = null;
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(TEMPDIR + tmpName+"/"+tmpName+".sh"));
        writer.write(sourceSh);
        writer.close();
    }

    public void removeDir(String dirName){
        FileSystemUtils.deleteRecursively(new File("./temp_dir/"+dirName));
    }



}
