package com.hust.minileetcode.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelGetContestResponse {
    private String contestId;
    private String contestName;
    private Integer contestTime;
}
