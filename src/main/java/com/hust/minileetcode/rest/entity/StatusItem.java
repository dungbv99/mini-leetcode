package com.hust.minileetcode.rest.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusItem {

    @Id
    @Column(name = "status_id")
    private String statusId;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "description")
    private String description;

}