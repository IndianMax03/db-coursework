package com.manu.roleplaybackend.controllers;

import com.manu.roleplaybackend.model.Friendship;
import com.manu.roleplaybackend.model.Review;
import com.manu.roleplaybackend.model.request.UpdateKarmaRequest;
import com.manu.roleplaybackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserControllerTest {

    private final String login = "login";

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        controller.getAll();
        verify(service, times(1)).getAll();
    }

    @Test
    void getUserByLogin() {
        controller.getUserByLogin(login);
        verify(service, times(1)).getByLogin(login);
    }

    @Test
    void becomeMaster() {
        controller.becomeMaster(login);
        verify(service, times(1)).becomeMaster(login);
    }

    @Test
    void getUserRoles() {
        controller.getUserRoles(login);
        verify(service, times(1)).getUserRoles(login);
    }

    @Test
    void getUserCharacters() {
        controller.getUserCharacters(login);
        verify(service, times(1)).getUserCharacters(login);
    }

    @Test
    void getUserGames() {
        controller.getUserGames(login);
        verify(service, times(1)).getUserGames(login);
    }

    @Test
    void updateKarma() {
        UpdateKarmaRequest request = new UpdateKarmaRequest();
        controller.updateKarma(request);
        verify(service, times(1)).updateKarma(request);
    }

    @Test
    void updateFriendRequestStatus() {
        Friendship request = new Friendship();
        controller.updateFriendRequestStatus(request);
        verify(service, times(1)).updateFriendRequestStatus(request);
    }

    @Test
    void friendRequest() {
        Friendship request = new Friendship();
        controller.friendRequest(request);
        verify(service, times(1)).friendRequest(request);
    }

    @Test
    void getFriendsByLogin() {
        controller.getFriendsByLogin(login);
        verify(service, times(1)).getFriends(login);
    }

    @Test
    void leaveReview() {
        Review review = new Review();
        controller.leaveReview(review);
        verify(service, times(1)).leaveReview(review);
    }

    @Test
    void getUserReviews() {
        controller.getUserReviews(login);
        verify(service, times(1)).getReviews(login);
    }
}