package com.hust.minileetcode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ModelGetTestCase {
    private boolean viewMore;
    private String testCase;
    private String correctAns;
    private int point;
}
