package com.hust.minileetcode.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contest_problem_test_case")
public class TestCase {
    @Id
    @Column(name = "test_case_id")
    private UUID testCaseId;

    @Column(name = "test_case_point")
    private int testCasePoint;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "contest_problem_id", referencedColumnName = "contest_problem_id")
    private ContestProblem contestProblem;

}
