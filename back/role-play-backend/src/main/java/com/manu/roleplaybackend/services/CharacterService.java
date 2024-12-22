package com.manu.roleplaybackend.services;

import com.manu.roleplaybackend.model.*;
import com.manu.roleplaybackend.model.Character;
import com.manu.roleplaybackend.model.request.CharacterRequest;
import com.manu.roleplaybackend.model.request.LobbyInformation;
import com.manu.roleplaybackend.repositories.*;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@NoArgsConstructor
public class CharacterService {

    @Autowired
    JdbcTemplate template;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    LobbyRepository lobbyRepository;

    @Autowired
    LobbyRequestRepository lobbyRequestRepository;

    @Autowired
    UserRepository userRepository;


    public ResponseEntity<Object> createCharacter(Character character) {
        if (!character.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid character data");
        }
        return new ResponseEntity<Object>(characterRepository.save(character), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> createLobbyRequest(LobbyRequest lobbyRequest) {
        if (!lobbyRequest.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid lobby request data");
        }
        Optional<Character> opCharacter = characterRepository.findById(lobbyRequest.getCharacterId());
        Optional<Game> opGame = gameRepository.findById(lobbyRequest.getGameId());

        if (opCharacter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no character with id = " + lobbyRequest.getCharacterId());
        }

        if (opGame.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game with id = " + lobbyRequest.getGameId());
        }

        Character character = opCharacter.get();

        if (!character.getCurrentStatus().equals("free")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot register busy character!");
        }

        Game game = opGame.get();

        if (!Objects.equals(character.getGameSystemId(), game.getGameSystemId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game systems don't match!");
        }

        Lobby lobby = lobbyRepository.findByGameId(lobbyRequest.getGameId());
        lobbyRequest.setLobbyId(lobby.getId());
        LobbyRequest savedRequest = lobbyRequestRepository.save(lobbyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    public ResponseEntity<Object> updateRequest(LobbyRequest lobbyRequest) {
        Optional<LobbyRequest> opLobbyRequest = lobbyRequestRepository.findById(lobbyRequest.getId());
        if (opLobbyRequest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no lobby request with id = " + lobbyRequest.getId());
        }

        LobbyRequest existingLobbyRequest = opLobbyRequest.get();
        existingLobbyRequest.setCurrentStatus(lobbyRequest.getCurrentStatus());
        LobbyRequest result = lobbyRequestRepository.save(existingLobbyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<Object> getLobbyInformationByCharacterId(Integer characterId) {
        String sql = "select characters.id chid, characters.name name , characters.picture picture, characters.game_system_id game_system_id, characters.user_id user_id, characters.current_status chstatus, characters.stats stats, lobby_requests.id lobreqid, lobby_requests.current_status lobreqstat from characters join lobby_requests on characters.id=lobby_requests.character_id join lobbies on lobby_requests.lobby_id=lobbies.id join games on lobbies.game_id=games.id where (lobby_requests.character_id=" + characterId + " and lobby_requests.current_status <> 'rejected' and games.current_status <> 'finished')";
        CharacterRequest characterRequest = null;
        try {
            characterRequest = template.queryForObject(sql, new RowMapper<CharacterRequest>() {
                @Override
                @Nullable
                public CharacterRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CharacterRequest characterRequest = new CharacterRequest(
                            rs.getInt("chid"),
                            rs.getString("name"),
                            rs.getBytes("picture"),
                            rs.getInt("game_system_id"),
                            rs.getInt("user_id"),
                            rs.getString("chstatus"),
                            rs.getBytes("stats"),
                            rs.getInt("lobreqid"),
                            rs.getString("lobreqstat")
                    );
                    return characterRequest;
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }

        if (characterRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your character is not yet registered in any of the lobbies");
        }

        Integer gameId = null;
        sql = "select games.id from characters join lobby_requests on lobby_requests.character_id=characters.id join lobbies on lobby_requests.lobby_id=lobbies.id join games on lobbies.game_id=games.id where (lobby_requests.character_id=" + characterId + " and lobby_requests.current_status <> 'rejected' and games.current_status <> 'finished')";

        try {
            gameId = template.queryForObject(sql, Integer.class);
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }

        if (gameId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find game for character with id = " + characterId);
        }

        Game game = gameRepository.findById(gameId).get();
        Lobby lobby = lobbyRepository.findById(gameId).get();
        User master = userRepository.findById(game.getMasterId()).get();

        sql = "select characters.id chid, characters.name name, characters.picture picture, characters.game_system_id game_system_id, characters.user_id user_id, characters.current_status chstatus, characters.stats stats, lobby_requests.id lobreqid, lobby_requests.current_status lobreqstat from characters join lobby_requests on characters.id=lobby_requests.character_id where lobby_requests.lobby_id=" + lobby.getId();
        List<CharacterRequest> characterRequests = null;
        try {
            characterRequests = template.query(sql, new RowMapper<CharacterRequest>() {
                @Override
                @Nullable
                public CharacterRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CharacterRequest characterRequest = new CharacterRequest(
                            rs.getInt("chid"),
                            rs.getString("name"),
                            rs.getBytes("picture"),
                            rs.getInt("game_system_id"),
                            rs.getInt("user_id"),
                            rs.getString("chstatus"),
                            rs.getBytes("stats"),
                            rs.getInt("lobreqid"),
                            rs.getString("lobreqstat")
                    );
                    return characterRequest;
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }

        return ResponseEntity.status(HttpStatus.OK).body(new LobbyInformation(game, lobby, master, characterRequests));

    }

}
