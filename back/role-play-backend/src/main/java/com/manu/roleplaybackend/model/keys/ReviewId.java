package com.manu.roleplaybackend.model.keys;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class ReviewId implements Serializable {

    private Integer reviewerId;
    private Integer recipientId;

}
