package com.manu.roleplaybackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    Review validReview;

    @BeforeEach
    void setUp() {
        validReview = new Review(1, 2, "Отличный сокомандник!", new Timestamp(System.currentTimeMillis()), 5);
    }

    @Test
    void isValidWithValid() {
        assertTrue(validReview.isValid());
    }

    @Test
    void isInvalidWithNegativeReviewerId() {
        validReview.setReviewerId(-1);
        assertFalse(validReview.isValid());
    }

    @Test
    void isInvalidWithNullReviewerId() {
        validReview.setReviewerId(null);
        assertFalse(validReview.isValid());
    }

    @Test
    void isInvalidWithNegativeRecipientId() {
        validReview.setRecipientId(-1);
        assertFalse(validReview.isValid());
    }

    @Test
    void isInvalidWithNullRecipientId() {
        validReview.setRecipientId(null);
        assertFalse(validReview.isValid());
    }

    @Test
    void isInvalidWithRatingGreaterThan5() {
        validReview.setRating(6);
        assertFalse(validReview.isValid());
    }

    @Test
    void isInvalidWithRatingLessThan0() {
        validReview.setRating(-1);
        assertFalse(validReview.isValid());
    }

    @Test
    void isInvalidWithEqualReviewerIdAndRecipientId() {
        validReview.setReviewerId(validReview.getRecipientId());
        assertFalse(validReview.isValid());
    }

}