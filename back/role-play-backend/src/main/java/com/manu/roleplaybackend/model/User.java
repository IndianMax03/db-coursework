package com.manu.roleplaybackend.model;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "karma")
    private Integer karma = 0;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "telegram_tag")
    private String telegramTag;

    @Column(name = "vk_tag")
    private String vkTag;

    @Column(name = "current_status")
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
