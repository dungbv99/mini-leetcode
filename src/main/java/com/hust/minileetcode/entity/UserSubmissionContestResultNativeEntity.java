package com.hust.minileetcode.entity;

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
//@Table(name = "user_submission_contest_result")
@Table(name = "user_submission_contest_result_new")
public class UserSubmissionContestResultNativeEntity {
    @Id
    @Column(name = "user_submission_contest_result_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userSubmissionContestResultId;

    @Column(name = "contest_id")
    private String contestId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "point")
    private int point;

    @Column(name = "created_stamp")
    private Date createdAt;

    @Column(name = "last_updated_stamp")
    private Date updateAt;
}
