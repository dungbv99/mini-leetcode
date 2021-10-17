package com.hust.minileetcode.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "test_case")
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



    @JoinTable(name = "contest_problem_test_case",
            joinColumns = @JoinColumn(name = "test_case_id", referencedColumnName = "test_case_id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id", referencedColumnName = "problem_id")
    )
    @OneToOne(fetch = FetchType.LAZY)
    private ContestProblem contestProblem;
}
