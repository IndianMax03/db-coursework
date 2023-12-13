package com.manu.roleplaybackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    
    private Integer id;
    private String login;
    private String name;
    private String password;
    private byte[] picture;
    private Integer karma = 0;
    private String timezone;
    private String telegramTag;
    private String vkTag;
    private String userStatus;
    
}
