package com.manu.roleplaybackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Character {
    
    private Integer id;
    private String name;
    private byte[] picture;
    private Integer gameSystemId;
    private Integer userId;
    private String currentStatus = "free";
    private byte[] stats;

    public boolean validName() {
        return this.name != null && !this.name.trim().isEmpty();
    }

    public boolean validGameSystemId() {
        return this.gameSystemId != null && this.gameSystemId > 0;
    }

    public boolean validUserId() {
        return this.userId != null && this.userId > 0;
    }

    public boolean isValid() {
        return validName() && validGameSystemId() && validUserId();
    }

}
