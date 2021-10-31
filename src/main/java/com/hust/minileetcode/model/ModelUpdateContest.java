package com.hust.minileetcode.model;

import lombok.Data;

import java.util.List;

@Data
public class ModelUpdateContest {
    private String contestName;
    private int contestSolvingTime;
    private List<String> problemIds;
}
