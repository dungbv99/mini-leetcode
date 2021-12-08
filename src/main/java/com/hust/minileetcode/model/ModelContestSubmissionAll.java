package com.hust.minileetcode.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ModelContestSubmissionAll {
    private List<ModelContestSubmission> contents;
}
