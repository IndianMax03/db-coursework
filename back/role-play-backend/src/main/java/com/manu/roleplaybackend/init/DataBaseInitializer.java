package com.manu.roleplaybackend.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Slf4j
public class DataBaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String path = "src/main/resources/sql/insert/default_data.sql";

    @Override
    public void run(String... args) throws Exception {
        try {

            log.info("=====> INSERTING DEFAULT DATA");
            String query = new String(Files.readAllBytes(Paths.get(path)));
            jdbcTemplate.execute(query);
            log.info("=====> DEFAULT DATA INSERTED SUCCESSFULLY");

        } catch (Exception e) {
            log.info("=====> ERROR DURING INSERTING DEFAULT DATA");
            System.out.println(e);

        }
    }
}
