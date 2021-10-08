package com.hust.minileetcode.utils.executor;




public class GccExecutor {
    private static final String buildCmd = "g++ -o main main.cpp";
    private static final String suffixes =".cpp";
    private static final String SHFileStart = "#!/bin/bash\n";

    public GccExecutor(){

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
                + buildCmd +"\n"
                + "cat testcase.txt | timeout " + timeLimit +"s " +"./main" + "\n"
                + "cd .. \n"
                + "rm -rf " + tmpName + " & "+"\n"
                + "rm -rf " + tmpName+".sh" + " & "+"\n"
                ;
        return sourceSH;
    }
}
