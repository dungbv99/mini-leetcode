package com.hust.minileetcode.entity;

import com.hust.minileetcode.rest.entity.UserLogin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contest_problem")
public class ContestProblem {
    @Id
    @Column(name = "problem_id")
    private String problemId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_source_code_id", referencedColumnName = "problem_source_code_id")
    private ProblemSourceCode problemSourceCode;

    @Column(name = "problem_name")
    private String problemName;

    @Column(name = "problem_description")
    private String problemDescription;

//    @OneToOne(cascade = CascadeType.ALL)
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
