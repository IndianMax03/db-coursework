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
    private String currentStatus;

}
