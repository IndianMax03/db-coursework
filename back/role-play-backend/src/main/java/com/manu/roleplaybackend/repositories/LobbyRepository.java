package com.manu.roleplaybackend.repositories;

import com.manu.roleplaybackend.model.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LobbyRepository extends JpaRepository<Lobby, Integer> {

    Lobby findByGameId(Integer id);

}
