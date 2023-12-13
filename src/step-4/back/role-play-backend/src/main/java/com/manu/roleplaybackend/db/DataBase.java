package com.manu.roleplaybackend.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.model.User;

@Component
public class DataBase {

    @Autowired
    JdbcTemplate template;
    
    public DataBase() {

    }

    public boolean addUser(User user) {
        String slq = "insert into users values (?, ?, ?, ?, ?, ?, ?, ?, ?, cast(? as user_status))";
        template.update(slq, user.getId(), user.getLogin(), user.getName(), user.getPassword(), user.getPicture(), user.getKarma(), user.getTimezone(), user.getTelegramTag(), user.getVkTag(), user.getUserStatus());
        return true;
    }

}
