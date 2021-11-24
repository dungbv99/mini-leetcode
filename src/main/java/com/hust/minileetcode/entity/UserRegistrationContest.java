package com.hust.minileetcode.entity;

import com.hust.minileetcode.rest.entity.UserLogin;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "contest_problem")
public class UserRegistrationContest {
    @Id
    @Column(name = "user_registration_contest_id")
    private UUID id;

    @JoinColumn(name = "user_id", referencedColumnName = "user_login_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserLogin userLogin;

    @JoinColumn(name = "contest_id", referencedColumnName = "contest_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Contest contest;

    @Column(name = "status")
    private String status;
}