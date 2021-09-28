package com.hust.minileetcode.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "problem_source_code")
public class ProblemSourceCode {
    @Id
    @Column(name = "problem_source_code_id")
    private String problemSourceCodeId;

    @Column(name = "base_source")
    private String baseSource;

    @Column(name = "main_source")
    private String mainSource;

    @Column(name = "problem_function_default_source")
    private String problemFunctionDefaultSource;

    @Column(name = "problem_function_solution")
    private String problemFunctionSolution;

    @Column(name = "language")
    private String language;

    @JoinTable(name = "contest_problem_problem_source_code",
            joinColumns = @JoinColumn(name = "problem_source_code_id", referencedColumnName = "problem_source_code_id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id", referencedColumnName = "problem_id")
    )
    @OneToOne(fetch = FetchType.EAGER)
    private ContestProblem contestProblem;

    public String createSolutionSourceCode(){
        return this.getBaseSource() + "\n" + this.getProblemFunctionSolution() + "\n" + this.getMainSource();
    }
}
