package com.manu.roleplaybackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UpdateKarmaRequest {
    
    private Integer senderUserId;
    private Integer receiverUserId;
    private boolean increase = false;

    public boolean validSenderUserId() {
        return this.senderUserId != null && this.senderUserId > 0;
    }

    public boolean validReceiverUserId() {
        return this.receiverUserId != null && this.receiverUserId > 0;
    }

    public boolean validCombination() {
        return this.senderUserId != this.receiverUserId;
    }

    public boolean isValid() {
        return validSenderUserId() && validReceiverUserId() && validCombination();
    }

}
