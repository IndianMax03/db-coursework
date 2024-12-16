package com.manu.roleplaybackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
@Table(name = "characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "game_system_id")
    private Integer gameSystemId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "cuurent_status")
    private String currentStatus = "free";

    @Column(name = "stats")
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
