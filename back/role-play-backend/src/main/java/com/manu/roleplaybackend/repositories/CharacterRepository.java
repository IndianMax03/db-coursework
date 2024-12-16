package com.manu.roleplaybackend.repositories;

import com.manu.roleplaybackend.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Integer> {

    List<Character> findAllByUserId(Integer userId);

}
