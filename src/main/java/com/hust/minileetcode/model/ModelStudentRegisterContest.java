package com.hust.minileetcode.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelStudentRegisterContest {
    private String courseId;
    private String userId;
}
