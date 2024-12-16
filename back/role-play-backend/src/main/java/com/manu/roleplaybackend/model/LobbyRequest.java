package com.manu.roleplaybackend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "lobby_requests")
public class LobbyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "lobby_id")
    private Integer lobbyId;

    @Column(name = "game_id")
    private Integer gameId;

    @Column(name = "character_id")
    private Integer characterId;

    @Column(name = "current_status")
    private String currentStatus = "on-review";

    public boolean validGameId() {
        return this.gameId != null && this.gameId > 0;
    }

    public boolean validCharacterId() {
        return this.characterId != null && this.characterId > 0;
    }

    public boolean isValid() {
        return validCharacterId() && validGameId();
    }

}
