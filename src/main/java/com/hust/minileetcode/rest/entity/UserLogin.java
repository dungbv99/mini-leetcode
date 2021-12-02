package com.hust.minileetcode.rest.entity;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogin {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @Column(name = "user_login_id", updatable = false, nullable = false)
    private String userLoginId;

    @Column(name = "current_password")
    private String password;

    private String passwordHint;

    private String otpSecret;

    private boolean isSystem;

    private boolean enabled;

    private boolean hasLoggedOut;

    private boolean requirePasswordChange;

    private Integer successiveFailedLogins;

    private String clientToken;

    private int otpResendNumber;

    private String email;


    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Person person;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_login_security_group",
            joinColumns = @JoinColumn(name = "user_login_id", referencedColumnName = "user_login_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
    private Set<SecurityGroup> roles;
    private Date disabledDateTime;

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

}