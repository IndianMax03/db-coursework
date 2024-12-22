package com.manu.roleplaybackend.repositories;

import com.manu.roleplaybackend.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findAllByMasterId(Integer userId);

}
