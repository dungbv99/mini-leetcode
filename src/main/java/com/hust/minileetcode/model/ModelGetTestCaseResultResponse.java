package com.hust.minileetcode.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class ModelGetTestCaseResultResponse {
    private String result;
    private String status;
}
