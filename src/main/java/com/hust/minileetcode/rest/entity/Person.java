package com.hust.minileetcode.rest.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

/**
 * Person
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    @Id
    @Column(name = "person_id")
    private UUID personId;

    //@Column(name="first_name")
    private String firstName;

    //@Column(name="middle_name")
    private String middleName;

    //@Column(name="last_name")
    private String lastName;

    //@Column(name="gender")
    private String gender;

    //@Column(name="birth_date")
    private Date birthDate;

    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    @ManyToOne
    private Status status;

    //private String birthDate;
    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public Person(UUID personId, String firstName, String middleName, String lastName, String gender, Date birthDate) {
        this.personId = personId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public BasicInfoModel getBasicInfoModel() {
        return new BasicInfoModel(personId, firstName + " " + middleName + " " + lastName, gender);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BasicInfoModel {

        private UUID personId;
        private String fullName;
        private String gender;
    }


    @Transient
    String name;

    @Transient
    String userName;


    @Override
    public String toString() {
        return "Person{" +
                "partyId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
