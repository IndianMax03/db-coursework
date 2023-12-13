package com.manu.roleplaybackend.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.model.Game;
import com.manu.roleplaybackend.model.User;

@Component
public class DataBase {

    @Autowired
    JdbcTemplate template;
    
    public DataBase() {

    }

    public boolean addUser(User user) {
        String slq = "insert into users values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?::user_status)";
        template.update(slq, user.getId(), user.getLogin(), user.getName(), user.getPassword(), user.getPicture(), user.getKarma(), user.getTimezone(), user.getTelegramTag(), user.getVkTag(), user.getUserStatus());
        return true;
    }

    public boolean addGame(Game game) {
        String sql = "insert into games values (?, ?, ?, ?, ?, ?::timestamp, ?::game_status, ?::timestamp, ?)";
        template.update(sql, game.getId(), game.getName(), game.getGameSystemId(), game.getPicture(), game.getMasterId(), game.getCreationDate(), game.getCurrentStatus(), game.getFinishDate(), game.getDescription());
        return true;
    }

}
