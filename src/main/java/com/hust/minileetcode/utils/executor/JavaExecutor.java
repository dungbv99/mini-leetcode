package com.hust.minileetcode.utils.executor;

public class JavaExecutor {
    private static final String buildCmd = "javac Main.java";
    private static final String suffixes =".java";
    private static final String SHFileStart = "#!/bin/bash\n";

    public JavaExecutor(){

    }

    public String generateScriptFileWithTestCaseAndCorrectSolution(String source, String testCase, String tmpName, int timeLimit){
        String sourceSH = SHFileStart
                + "mkdir -p " + tmpName +"\n"
                + "cd " + tmpName +"\n"
                + "cat <<EOF >> Main"  + suffixes + "\n"
                + source + "\n"
                + "EOF" + "\n"
                + "cat <<EOF >> testcase.txt \n"
                + testCase +"\n"
                + "EOF" + "\n"
                + buildCmd +"\n"
                + "cat testcase.txt | timeout " + timeLimit +"s " +"java Main" + "\n"
                + "cd .. \n"
                + "rm -rf " + tmpName + " & "+"\n"
                + "rm -rf " + tmpName+".sh" + " & "+"\n"
                ;
        return sourceSH;
    }
}
