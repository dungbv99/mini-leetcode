package com.hust.minileetcode.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ModelGetContestDetailResponse {
    private String contestId;
    private String contestName;
    private int contestTime;
    private List<ModelGetProblemDetailResponse> list;
    private boolean unauthorized;
}
