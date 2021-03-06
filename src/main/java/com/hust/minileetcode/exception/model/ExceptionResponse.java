package com.hust.minileetcode.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private int code;
    private String message;
}
