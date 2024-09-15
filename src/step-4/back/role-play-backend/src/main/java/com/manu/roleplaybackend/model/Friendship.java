package com.manu.roleplaybackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Friendship {
    
    private Integer senderId;
    private Integer receiverId;
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
