package com.hust.minileetcode.utils.executor;

import com.hust.minileetcode.entity.TestCaseEntity;

import java.util.List;

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
                + "FILE=Main.class" +"\n"
                +"if test -f \"$FILE\"; then" +"\n"
                + "    cat testcase.txt | timeout " + timeLimit +"s " +"java Main  && echo -e \"\\nSuccessful\"  || echo Time Limit Exceeded" + "\n"
                + "else\n"
                + "  echo Compile Error\n"
                + "fi" + "\n"
                + "cd .. \n"
                + "rm -rf " + tmpName + " & "+"\n"
                + "rm -rf " + tmpName+".sh" + " & "+"\n";
        return sourceSH;
    }

    public String checkCompile(String source, String tmpName){
        String sourceSH = SHFileStart
                + "mkdir -p " + tmpName +"\n"
                + "cd " + tmpName +"\n"
                + "cat <<EOF >> Main"  + suffixes + "\n"
                + source + "\n"
                + "EOF" + "\n"
                + buildCmd +"\n"
                + "FILE=Main.class" +"\n"
                +"if test -f \"$FILE\"; then" +"\n"
                + "  echo Successful\n"
                + "else\n"
                + "  echo Compile Error\n"
                + "fi" + "\n"
                + "cd .. \n"
                + "rm -rf " + tmpName + " & "+"\n"
                + "rm -rf " + tmpName+".sh" + " & "+"\n";
        return sourceSH;
    }

    public String genSubmitScriptFile(List<TestCaseEntity> testCases, String source, String tmpName, int timeout){
        String genTestCase = "";
        for(int i = 0; i < testCases.size(); i++){
            String testcase = "cat <<EOF >> testcase" + i + ".txt \n"
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
                + "start=$(date +%s%N)\n"
                + "while [ \"$n\" -lt " + testCases.size()+" ]"+"\n"
                + "do\n"
                + "f=\"testcase\"$n\".txt\"" +"\n"
                + "cat $f | timeout " + timeout + "s" +" java Main" +"\n"
                + "echo testcasedone\n"
                + "n=`expr $n + 1`\n"
                + "done\n"
                + "end=$(date +%s%N)\n"
                + "echo \n"
                + "echo \"$(($(($end-$start))/1000000))\"\n"
                + "echo successful\n"
                + "else\n"
                + "echo Compile Error\n"
                + "fi" + "\n"
                + "cd .. \n"
                + "rm -rf " + tmpName + " & "+"\n"
                + "rm -rf " + tmpName+".sh" + " & "+"\n";
        return sourceSH;
    }
}
