package com.manu.roleplaybackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Game {
    
    private Integer id;
    private String name;
    private Integer gameSystemId;
    private byte[] picture;
    private Integer masterId;
    private String creationDate;
    private String currentStatus = "not-started";
    private String finishDate;
    private String description;

    public boolean validName() {
        return this.name != null && !this.name.trim().isEmpty() && this.name.length() <= 32;
    }

    public boolean validGameSystemId() {
        return this.gameSystemId != null && this.gameSystemId > 0;
    }

    public boolean validMasterId() {
        return this.masterId != null && this.masterId > 0;
    }

    public boolean isValid() {
        return validName() && validGameSystemId() && validMasterId();
    }

}
