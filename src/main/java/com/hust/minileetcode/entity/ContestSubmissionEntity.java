package com.hust.minileetcode.entity;

import com.hust.minileetcode.rest.entity.UserLogin;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
//@Table(name = "contest_problem")
@Table(name = "contest_submission_new")
public class ContestSubmissionEntity {
    @Id
    @Column(name = "contest_submission_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID contestSubmissionId;

    @JoinColumn(name = "problem_id", referencedColumnName = "problem_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProblemEntity problem;

    @JoinColumn(name = "contest_id", referencedColumnName = "contest_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContestEntity contest;

    @JoinColumn(name = "user_submission_id", referencedColumnName = "user_login_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserLogin userLogin;

//    @JoinColumn(name = "problem_submission_id", referencedColumnName = "problem_submission_id")
//    @OneToOne
//    private ProblemSubmissionEntity problemSubmission;

    @Column(name = "test_case_pass")
    private String testCasePass;

    @Column(name = "source_code")
    private String sourceCode;

    @Column(name = "source_code_language")
    private String sourceCodeLanguage;

    @Column(name = "runtime")
    private Long runtime;

    @Column(name = "memory_usage")
    private float memoryUsage;

    @Column(name = "point")
    private Integer point;

    @Column(name = "status")
    private String status;

    @Column(name = "created_stamp")
    private Date createdAt;

    @Column(name = "last_updated_stamp")
    private Date updateAt;


}
