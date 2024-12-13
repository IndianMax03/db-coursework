package com.manu.roleplaybackend.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Review {
    
    private Integer reviewerId;
    private Integer recipientId;
    private String content;
    private Timestamp date;
    private Integer rating;

    public boolean validReviewerId() {
        return this.reviewerId != null && this.reviewerId > 0;
    }

    public boolean validRecipientId() {
        return this.recipientId != null && this.recipientId > 0;
    }

    public boolean validRating() {
        return this.rating != null && this.rating >= 0 && this.rating <= 5;
    }

    public boolean validCombination() {
        return this.reviewerId != this.recipientId;
    }

    public boolean isValid() {
        return validReviewerId() && validRecipientId() && validRating() && validCombination();
    }

}
