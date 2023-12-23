package com.manu.roleplaybackend.model.request;

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
public class CharacterRequest {

    private Integer characterId;
    private String name;
    private byte[][] picture;
    private Integer gameSystemId;
    private Integer userId;
    private String currentStatus = "free";
    private byte[][] stats;
    private Integer lobbyRequestId;
    private String requestStatus;
    
}
