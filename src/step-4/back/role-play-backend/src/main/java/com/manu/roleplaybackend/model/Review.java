package com.manu.roleplaybackend.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Review {
    
    private Integer reviewerId;
    private Integer recipientId;
    private String content;
    private Timestamp date;
    private Integer rating;

}
