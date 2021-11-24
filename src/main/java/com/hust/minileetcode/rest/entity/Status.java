package com.hust.minileetcode.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @Id
    @Column(name = "status_id")
    private String id;

    private String statusCode;
    private String sequenceId;

    //@Column(name="description")
    private String description;

    private Date createdStamp;
    private Date lastUpdatedStamp;


    public enum StatusEnum {
        PERSON_ENABLED("PERSON_ENABLED"), PERSON_DISABLE("PERSON_DISABLE");

        private String value;

        StatusEnum(String value){
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }
}
