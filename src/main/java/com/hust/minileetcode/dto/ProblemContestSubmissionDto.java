package com.hust.minileetcode.dto;

import com.hust.minileetcode.entity.ProblemEntity;
import com.hust.minileetcode.rest.entity.UserLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemContestSubmissionDto {
    private String userId;
    private Integer point;
}
