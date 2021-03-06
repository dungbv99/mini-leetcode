package com.hust.minileetcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContestSubmission {
    private UUID contestSubmissionId;
    private String problemId;
    private String contestId;
    private String userId;
    private String testCasePass;
    private String sourceCodeLanguage;
    private Integer point;
    private String status;
    private String createAt;
}
