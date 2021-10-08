package com.hust.minileetcode.utils.executor;

public class Python3Executor {
    private static final String suffixes =".py";
    private static final String SHFileStart = "#!/bin/bash\n";

    public Python3Executor(){

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
                + "cat testcase.txt | timeout " + timeLimit +"s " +"python3 main.py" + "\n"
                + "cd .. \n"
                + "rm -rf " + tmpName + " & "+"\n"
                + "rm -rf " + tmpName+".sh" + " & "+"\n"
                ;
        return sourceSH;
    }
}
