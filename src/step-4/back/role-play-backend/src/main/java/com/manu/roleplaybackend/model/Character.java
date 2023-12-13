package com.manu.roleplaybackend.model;

public class Character {
    
    private Integer id;
    private String name;
    private byte[] picture;
    private Integer gameSystemId;
    private Integer userId;
    
    public Character() {
        this.id = 1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Integer getGameSystemId() {
        return gameSystemId;
    }

    public Integer getUserId() {
        return userId;
    }

}
