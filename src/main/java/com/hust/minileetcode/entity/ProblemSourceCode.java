package com.hust.minileetcode.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "problem_source_code")
public class ProblemSourceCode {
    @Id
    @Column(name = "problem_source_code_id")
    private UUID problemSourceCodeId;

    @Column(name = "base_source")
    private String baseSource;

    @Column(name = "main_source")
    private String mainSource;

    @Column(name = "problem_function_default_source")
    private String problemFunctionDefaultSource;

    @Column(name = "problem_function_solution")
    private String problemFunctionSolution;
}
