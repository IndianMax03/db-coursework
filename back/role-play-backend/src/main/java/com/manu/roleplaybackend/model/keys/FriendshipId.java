package com.manu.roleplaybackend.model.keys;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class FriendshipId implements Serializable {

    private Integer senderId;
    private Integer receiverId;

}
