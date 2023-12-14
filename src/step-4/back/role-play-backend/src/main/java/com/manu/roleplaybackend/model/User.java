package com.manu.roleplaybackend.model;

import java.util.Arrays;
import java.util.List;

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
    private Integer karma;
    private String timezone;
    private String telegramTag;
    private String vkTag;
    private String currentStatus;
 
    public boolean isValid() {
        boolean notNull = this.login != null && this.name != null && this.password != null && this.karma != null && this.currentStatus != null;
        boolean notEmpty = !this.login.trim().isEmpty() && !this.name.trim().isEmpty() && !this.password.trim().isEmpty();
        return notNull && notEmpty;
    }

    public List<Object> getParamsList() {
        return Arrays.asList(this.login, this.name, this.password, this.picture, this.karma, this.timezone, this.telegramTag, this.vkTag, this.currentStatus);
    }
    
}
