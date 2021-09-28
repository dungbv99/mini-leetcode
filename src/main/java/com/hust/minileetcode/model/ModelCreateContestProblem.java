package com.hust.minileetcode.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelCreateContestProblem {
    private String problemId;
    private String problemName;
    private String problemDescription;
    private int timeLimit;
    private int memoryLimit;
    private String levelId;
    private String categoryId;
}
