package com.hust.minileetcode.entity;

import com.hust.minileetcode.rest.entity.UserLogin;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "problem_submission")
public class ProblemSubmission {
    @Id
    @Column(name = "problem_submission_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID problemSubmissionId;

    @JoinColumn(name = "problem_id", referencedColumnName = "problem_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @JoinColumn(name = "submitted_by_user_login_id", referencedColumnName = "user_login_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserLogin userLogin;

    @Column(name = "source_code")
    private String sourceCode;

    @Column(name = "source_code_language")
    private String sourceCodeLanguages;

    @Column(name = "status")
    private String status;

    @Column(name = "score")
    private int score;

    @Column(name = "runtime")
    private String runtime;

    @Column(name = "memory_usage")
    private float memoryUsage;

    @Column(name = "created_stamp")
    private String timeSubmitted;

    @Column(name = "test_case_pass")
    private String testCasePass;

    @PrePersist
    protected void onCreate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        timeSubmitted = formatter.format(date);

    }
}
