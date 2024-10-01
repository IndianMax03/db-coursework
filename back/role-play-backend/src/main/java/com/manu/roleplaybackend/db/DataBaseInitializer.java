package com.manu.roleplaybackend.db;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String mainPath = "src/main/resources/sql/create/";
    private final String[] initFileNames = {"model", "indexes", "functions"};

    @Override
    public void run(String... args) throws Exception {
        String query;

        for (int i = 0; i < initFileNames.length; i++) {
            try {
                query = new String(Files.readAllBytes(Paths.get(mainPath + initFileNames[i] + ".sql")));
                jdbcTemplate.execute(query);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        // try {
        //     query = new String(Files.readAllBytes(Paths.get("src/main/resources/sql/insert/default_data.sql")));
        //     jdbcTemplate.execute(query);
        // } catch (Exception e) {
        //     System.out.println(e);
        // }
    }

}
