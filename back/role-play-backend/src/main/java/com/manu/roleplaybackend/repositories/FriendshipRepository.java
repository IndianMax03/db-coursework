package com.manu.roleplaybackend.repositories;

import com.manu.roleplaybackend.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    Friendship findBySenderIdAndReceiverId(Integer senderId, Integer receiverId);

}
