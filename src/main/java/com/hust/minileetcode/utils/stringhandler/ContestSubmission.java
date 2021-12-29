package com.hust.minileetcode.utils.stringhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContestSubmission {
    private String status;
    private Long runtime;
    private String testCasePass;
    private int score;
}
