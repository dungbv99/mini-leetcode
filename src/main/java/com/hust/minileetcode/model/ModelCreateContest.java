package com.hust.minileetcode.model;

import lombok.Data;

import java.util.List;

@Data
public class ModelCreateContest {
    private String contestId;
    private String contestName;
    private int contestSolvingTime;
    private List<String> problemIds;
}
