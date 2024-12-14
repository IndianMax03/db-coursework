package com.manu.roleplaybackend.repositories;

import com.manu.roleplaybackend.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRole, Integer> {

    List<UserRole> findAllByUserId(Integer userId);

}
