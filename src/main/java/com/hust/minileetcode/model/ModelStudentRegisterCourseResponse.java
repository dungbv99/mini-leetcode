package com.hust.minileetcode.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelStudentRegisterCourseResponse {
    private String status;
    private String message;
}
