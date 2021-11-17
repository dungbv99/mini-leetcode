package com.hust.minileetcode.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelContestSubmission {
    private String contestId;
    private String problemId;
    private String source;
    private String language;
}
