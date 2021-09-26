package com.hust.minileetcode.utils;

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

    public void createScriptFile(String source, ComputerLanguage.Languages languages, String tmpName ) throws IOException {
        String suffixes = "";
        String cmd = "";
        switch (languages){
            case CPP:
                suffixes = ".cpp";
                cmd = "g++ -o main main.cpp" + "\n" +"timeout 5s ./main" +"\n";
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
                + "mkdir -p " + tmpName +"\n"
                + "cd " + tmpName +"\n"
                + "cat <<EOF >> main"  + suffixes + "\n"
                + source + "\n"
                + "EOF" + "\n"
                + cmd + "\n"
                + "cd .. \n"
                + "rm -rf " + tmpName + "\n"
                + "rm -rf " + tmpName+".sh"
                ;
        File theDir = new File(TEMPDIR+tmpName);
        theDir.mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter(TEMPDIR + tmpName+"/"+tmpName+".sh"));
        writer.write(sourceSh);
        writer.close();
    }

    public void removeDir(String dirName){
        FileSystemUtils.deleteRecursively(new File("./temp_dir/"+dirName));
    }

}
