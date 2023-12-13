package com.manu.roleplaybackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Character {
    
    private Integer id;
    private String name;
    private byte[] picture;
    private Integer gameSystemId;
    private Integer userId;

}
