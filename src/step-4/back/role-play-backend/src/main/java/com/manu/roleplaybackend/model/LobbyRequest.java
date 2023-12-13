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
    private Integer characterId;
    private String currentStatus;

}
