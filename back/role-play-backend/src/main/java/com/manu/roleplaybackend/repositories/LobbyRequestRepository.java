package com.manu.roleplaybackend.repositories;

import com.manu.roleplaybackend.model.LobbyRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LobbyRequestRepository extends JpaRepository<LobbyRequest, Integer> {
}
