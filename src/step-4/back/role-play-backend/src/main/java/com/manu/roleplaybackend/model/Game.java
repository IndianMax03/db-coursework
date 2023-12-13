package com.manu.roleplaybackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Game {
    
    private Integer id;
    private String name;
    private Integer gameSystemId;
    private byte[] picture;
    private Integer masterId;
    private String creationDate;
    private String currentStatus;
    private String finishDate;
    private String description;

}
