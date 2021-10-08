package com.hust.minileetcode.utils.executor;

public class GolangExecutor {
    private static final String suffixes =".go";
    private static final String SHFileStart = "#!/bin/bash\n";
//    private static final String buildCmd = "javac Main.java";

    public GolangExecutor(){

    }

    public String generateScriptFileWithTestCaseAndCorrectSolution(String source, String testCase, String tmpName, int timeLimit){
        String sourceSH = SHFileStart
                + "mkdir -p " + tmpName +"\n"
                + "cd " + tmpName +"\n"
                + "cat <<EOF >> main"  + suffixes + "\n"
                + source + "\n"
                + "EOF" + "\n"
                + "cat <<EOF >> testcase.txt \n"
                + testCase +"\n"
                + "EOF" + "\n"
//                + buildCmd +"\n"
                + "cat testcase.txt | timeout " + timeLimit +"s " +"go run main.go" + "\n"
                + "cd .. \n"
                + "rm -rf " + tmpName + " & "+"\n"
                + "rm -rf " + tmpName+".sh" + " & "+"\n"
                ;
        return sourceSH;
    }
}
