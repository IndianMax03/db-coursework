package com.manu.roleplaybackend.services;

import com.manu.roleplaybackend.model.Lobby;
import com.manu.roleplaybackend.model.User;
import com.manu.roleplaybackend.model.request.CharacterRequest;
import com.manu.roleplaybackend.model.request.LobbyInformation;
import com.manu.roleplaybackend.repositories.GameRepository;
import com.manu.roleplaybackend.repositories.LobbyRepository;
import com.manu.roleplaybackend.repositories.UserRepository;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.model.Game;

import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor
public class GameService {

    @Autowired
    JdbcTemplate template;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    LobbyRepository lobbyRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Object> createGame(Game game) {
        if (!game.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid game data");
        }
        Game createdGame = gameRepository.save(game);
        Lobby gameLobby = new Lobby(null, createdGame.getId(), "online");
        lobbyRepository.save(gameLobby);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getGames() {
        return new ResponseEntity<>(gameRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Object> updateStatus(Game game) {
        Optional<Game> opGame = gameRepository.findById(game.getId());

        if (opGame.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game with id = " + game.getId());
        }

        Game existingGame = opGame.get();
        existingGame.setCurrentStatus(game.getCurrentStatus());

        gameRepository.save(existingGame);

        return ResponseEntity.status(HttpStatus.OK).body("Game status changed successfully");
    }

    public ResponseEntity<Object> getLobbyInformation( Integer gameId) {
        if (gameId == null || gameId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid game id");
        }

        Optional<Game> opGame = gameRepository.findById(gameId);
        if (opGame.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game with id = " + gameId);
        }
        Lobby lobby = lobbyRepository.findByGameId(gameId);

        Game game = opGame.get();

        User master = userRepository.findById(game.getMasterId()).get();

        String sql = "select characters.id chid, name, picture, game_system_id, user_id, characters.current_status chstatus, stats, lobby_requests.id lobreqid, lobby_requests.current_status lobreqstat from characters join lobby_requests on characters.id=lobby_requests.character_id where lobby_requests.lobby_id=" + lobby.getId();
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
