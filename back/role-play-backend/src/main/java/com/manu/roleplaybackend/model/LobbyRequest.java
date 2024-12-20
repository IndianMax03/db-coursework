package com.manu.roleplaybackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LobbyRequest {
    
    private Integer id;
    private Integer lobbyId;
    private Integer gameId;
    private Integer characterId;
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
