package com.hust.minileetcode.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@ToString
@Table(name = "contest_problem")
public class ContestProblem {
    @Id
    @Column(name = "problem_id")
    private String problemId;

    @JoinTable(name = "contest_problem_problem_source_code",
        joinColumns = @JoinColumn(name = "problem_id", referencedColumnName = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "problem_source_code_id", referencedColumnName = "problem_source_code_id")
    )
    @OneToMany(fetch = FetchType.LAZY)
    private List<ProblemSourceCode> problemSourceCode;

    @Column(name = "problem_name")
    private String problemName;

    @Column(name = "problem_description")
    private String problemDescription;

//    @OneToOne
//    @JoinColumn(name = "created_by_user_login_id", referencedColumnName = "user_login_id")
//    private UserLogin userLogin;

    @Column(name = "time_limit")
    private int timeLimit;

    @Column(name = "memory_limit")
    private int memoryLimit;

    @Column(name = "level_id")
    private String levelId;

    @Column(name = "category_id")
    private String categoryId;
}
