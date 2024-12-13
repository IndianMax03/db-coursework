package com.manu.roleplaybackend.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manu.roleplaybackend.model.Friendship;
import com.manu.roleplaybackend.model.Review;
import com.manu.roleplaybackend.model.request.UpdateKarmaRequest;
import com.manu.roleplaybackend.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        log.info("Got request: GET: users/");
        return userService.getAll();
    }

    @GetMapping("/{login}")
    public ResponseEntity<Object> getUserByLogin(@PathVariable String login) {
        log.info("Got request: GET: users/{}", login);
        return userService.getByLogin(login);
    }

    @GetMapping("/{login}/roles/become-master")
    public ResponseEntity<Object> becomeMaster(@PathVariable String login) {
        log.info("Got request: GET: users/{}/roles/become-master", login);
        return userService.becomeMaster(login);
    }

    @GetMapping("/{login}/roles")
    public ResponseEntity<Object> getUserRoles(@PathVariable String login) {
        log.info("Got request: GET: users/{}/roles", login);
        return userService.getUserRoles(login);
    }

    @GetMapping("/{login}/characters")
    public ResponseEntity<Object> getUserCharacters(@PathVariable String login) {
        log.info("Got request: GET: users/{}/characters", login);
        return userService.getUserCharacters(login);
    }

    @GetMapping("/{login}/games")
    public ResponseEntity<Object> getUserGames(@PathVariable String login) {
        log.info("Got request: GET: users/{}/games", login);
        return userService.getUserGames(login);
    }

    @PatchMapping("/karma")
    public ResponseEntity<Object> updateKarma(@RequestBody UpdateKarmaRequest updKarmaRequest) {
        log.info("Got request: PATCH: users/karma");
        return userService.updateKarma(updKarmaRequest);
    }

    @PatchMapping("/friendship")
    public ResponseEntity<Object> updateFriendRequestStatus(@RequestBody Friendship friendship) {
        log.info("Got request: PATCH: users/friendship");
        return userService.updateFriendRequestStatus(friendship);
    }

    @PostMapping("/friendship")
    public ResponseEntity<Object> friendRequest(@RequestBody Friendship friendship) {
        log.info("Got request: POST: users/friendship");
        return userService.friendRequest(friendship);
    }

    @GetMapping("/{login}/friends")
    public ResponseEntity<Object> getFriendsByLogin(@PathVariable String login) {
        log.info("Got request: GET: users/{}/friends", login);
        return userService.getFriends(login);
    }

    @PostMapping("/reviews")
    public ResponseEntity<Object> leaveReview(@RequestBody Review review) {
        log.info("Got request: POST: users/reviews");
        return userService.leaveReview(review);
    }

    @GetMapping("/{login}/reviews")
    public ResponseEntity<Object> getUserReviews(@PathVariable String login) {
        log.info("Got request: GET: users/{}/reviews", login);
        return userService.getReviews(login);
    }

}
