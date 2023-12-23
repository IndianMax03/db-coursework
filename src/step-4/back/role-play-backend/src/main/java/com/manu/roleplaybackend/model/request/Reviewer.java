package com.manu.roleplaybackend.model.request;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reviewer {
    
    private Integer id;
    private String login;
    private String name;
    private byte[][] picture;
    private Integer karma;
    private String timezone;
    private String telegramTag;
    private String vkTag;
    private String currentStatus;
    private String content;
    private Timestamp date;
    private Integer rating;

}
