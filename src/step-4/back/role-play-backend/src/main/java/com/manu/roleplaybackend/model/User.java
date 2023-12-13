package com.manu.roleplaybackend.model;

public class User {
    
    private final int id;
    private String login;
    private String name;
    private String password;
    private int karma;
    private String timezone;
    private String telegramTag;
    private String vkTag;
    private String userStatus;

    public User() {
        this.id = 1;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getKarma() {
        return karma;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getTelegramTag() {
        return telegramTag;
    }

    public String getVkTag() {
        return vkTag;
    }

    public String getUserStatus() {
        return userStatus;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", name=" + name + ", password=" + password + ", karma=" + karma
                + ", timezone=" + timezone + ", telegramTag=" + telegramTag + ", vkTag=" + vkTag + ", userStatus="
                + userStatus + "]";
    }

    

}
