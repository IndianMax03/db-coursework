package com.manu.roleplaybackend.model;

import java.util.Arrays;

public class Game {
    
    private Integer id;
    private String name;
    private Integer gameSystemId;
    private byte[] picture;
    private Integer masterId;
    private String creationDate;
    private String currentStatus;
    private String finishDate;
    private String description;
    
    public Game() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getGameSystemId() {
        return gameSystemId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", name=" + name + ", gameSystemId=" + gameSystemId + ", picture="
                + Arrays.toString(picture) + ", masterId=" + masterId + ", creationDate=" + creationDate
                + ", currentStatus=" + currentStatus + ", finishDate=" + finishDate + ", description=" + description
                + "]";
    }

}
