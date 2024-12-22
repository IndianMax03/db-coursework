package com.manu.roleplaybackend.model;

import java.sql.Timestamp;

import com.manu.roleplaybackend.model.keys.ReviewId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "reviews")
@IdClass(ReviewId.class)
public class Review {

    @Id
    @Column(name = "reviewer_user_id")
    private Integer reviewerId;

    @Id
    @Column(name = "recipient_user_id")
    private Integer recipientId;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Timestamp date = new Timestamp(System.currentTimeMillis());

    @Column(name = "rating")
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
