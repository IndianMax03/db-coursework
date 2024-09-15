package com.manu.roleplaybackend.model.request;

import java.util.List;

import com.manu.roleplaybackend.model.Game;
import com.manu.roleplaybackend.model.Lobby;
import com.manu.roleplaybackend.model.User;

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
public class LobbyInformation {
    
    Game game;
    Lobby lobby;
    User master;
    List<CharacterRequest> requests;

}
