package com.hust.minileetcode.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@ToString
@Table(name = "contest_problem_test_case")
public class TestCase {
    @Id
    @Column(name = "test_case_id")
    private UUID testCaseId;

    @Column(name = "test_case_point")
    private int testCasePoint;

    @Column(name = "test_case")
    private String testCase;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @JoinColumn(name = "contest_problem_id", referencedColumnName = "problem_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContestProblem contestProblem;

}
