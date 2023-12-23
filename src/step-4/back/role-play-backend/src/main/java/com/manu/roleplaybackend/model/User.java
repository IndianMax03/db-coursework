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
    private byte[][] picture;
    private Integer karma = 0;
    private String timezone;
    private String telegramTag;
    private String vkTag;
    private String currentStatus = "exists";

    public boolean validLogin() {
        return this.login != null && !this.login.trim().isEmpty() && this.login.length() <= 32;
    }

    public boolean validName() {
        return this.name != null && !this.name.trim().isEmpty() && this.name.length() <= 128;
    }

    public boolean validPassword() {
        return this.password != null && !this.password.trim().isEmpty() && this.password.length() <= 128;
    }

    public boolean validKarma() {
        return this.karma != null;
    }

    public boolean validCurrentStatus() {
        return this.currentStatus != null;
    }
 
    public boolean isValid() {
        return validLogin() && validName() && validPassword();
    }

    public List<Object> getParamsList() {
        return Arrays.asList(this.login, this.name, this.password, this.picture, this.karma, this.timezone, this.telegramTag, this.vkTag, this.currentStatus);
    }
    
}
