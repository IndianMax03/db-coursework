package com.manu.roleplaybackend.repositories;

import com.manu.roleplaybackend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
