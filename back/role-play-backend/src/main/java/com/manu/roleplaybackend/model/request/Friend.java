package com.manu.roleplaybackend.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Friend {

    private Integer id;
    private String login;
    private String name;
    private byte[] picture;
    private Integer karma = 0;
    private String timezone;
    private String telegramTag;
    private String vkTag;
    private String currentStatus;
    private String friendshipStatus;

}
