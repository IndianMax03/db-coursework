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
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "game_system_id")
    private Integer gameSystemId;

    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "master_id")
    private Integer masterId;

    @Column(name = "creation_date")
    private String creationDate;

    @Column(name = "current_status")
    private String currentStatus = "not-started";

    @Column(name = "finish_date")
    private String finishDate;

    @Column(name = "description")
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
