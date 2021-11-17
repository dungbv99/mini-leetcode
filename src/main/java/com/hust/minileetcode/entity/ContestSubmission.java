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
@Table(name = "contest_problem")
public class ContestSubmission {
    @Id
    @Column(name = "contest_submission_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID contestSubmissionId;

    @JoinColumn(name = "problem_id", referencedColumnName = "problem_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @JoinColumn(name = "contest_id", referencedColumnName = "contest_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Contest contest;

    @JoinColumn(name = "user_submission_id", referencedColumnName = "user_login_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserLogin userLogin;

    @Column(name = "point")
    private int point;

    @Column(name = "created_stamp")
    private Date createdAt;

    @Column(name = "last_updated_stamp")
    private Date updateAt;
}
