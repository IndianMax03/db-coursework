package com.manu.roleplaybackend.model;

import com.manu.roleplaybackend.model.keys.FriendshipId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "friendships")
@IdClass(FriendshipId.class)
public class Friendship {

    @Id
    @Column(name = "sender_user_id")
    private Integer senderId;

    @Id
    @Column(name = "receiver_user_id")
    private Integer receiverId;

    @Column(name = "current_status")
    private String currentStatus = "on-review";

    public boolean validSenderId() {
        return this.senderId != null && this.senderId > 0;
    }

    public boolean validReceiverId() {
        return this.receiverId != null && this.receiverId > 0;
    }

    public boolean validCombination() {
        return this.senderId != this.receiverId;
    }

    public boolean isValid() {
        return validSenderId() && validReceiverId() && validCombination();
    }

}
