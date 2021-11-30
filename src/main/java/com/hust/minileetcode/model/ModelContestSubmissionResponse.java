package com.hust.minileetcode.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ModelContestSubmissionResponse {
    UUID contestSubmissionID;
    String contestId;
    String problemId;
    String problemName;
    String submittedAt;
    Integer score;
    String testCasePass;
    String runTime;
    Float memoryUsage;
    String status;
    Float runtime;
}
