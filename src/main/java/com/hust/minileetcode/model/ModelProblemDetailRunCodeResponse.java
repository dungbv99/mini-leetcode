package com.hust.minileetcode.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelProblemDetailRunCodeResponse {
    String output;
    String expected;
    String status;
}
