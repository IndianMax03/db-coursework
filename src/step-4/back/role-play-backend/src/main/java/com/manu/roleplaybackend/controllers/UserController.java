package com.manu.roleplaybackend.controllers;


import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manu.roleplaybackend.model.Friendship;
import com.manu.roleplaybackend.model.Review;
import com.manu.roleplaybackend.model.request.TestPic;
import com.manu.roleplaybackend.model.request.UpdateKarmaRequest;
import com.manu.roleplaybackend.services.UserService;

@RestController
@RequestMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> testPic(@RequestParam("pic") MultipartFile pic, @ModelAttribute TestPic test) {
        System.out.println("---------------------------" + pic.getOriginalFilename());
        test.setFile(pic);
        test.convert();
        test.setFile(null);
        return ResponseEntity.status(HttpStatus.OK).body(test);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{login}")
    public ResponseEntity<Object> getUserByLogin(@PathVariable String login) {
        return userService.getByLogin(login);
    }

    @GetMapping("/roles/become-master/{login}")
    public ResponseEntity<Object> becomeMaster(@PathVariable String login) {
        return userService.becomeMaster(login);
    }

    @GetMapping("/roles/{login}")
    public ResponseEntity<Object> getUserRoles(@PathVariable String login) {
        return userService.getUserRoles(login);
    }

    @GetMapping("/characters/{login}")
    public ResponseEntity<Object> getUserCharacters(@PathVariable String login) {
        return userService.getUserCharacters(login);
    }

    @GetMapping("/games/{login}")
    public ResponseEntity<Object> getUserGames(@PathVariable String login) {
        return userService.getUserGames(login);
    }

    @PatchMapping("/update/karma")
    public ResponseEntity<Object> updateKarma(@RequestBody UpdateKarmaRequest updKarmaRequest) {
        return userService.updateKarma(updKarmaRequest);
    }

    @PatchMapping("/update/friendship")
    public ResponseEntity<Object> updateFriendRequestStatus(@RequestBody Friendship friendship) {
        return userService.updateFriendRequestStatus(friendship);
    }

    @PostMapping("/friendship")
    public ResponseEntity<Object> friendRequest(@RequestBody Friendship friendship) {
        return userService.friendRequest(friendship);
    }

    @GetMapping("/friends/{login}")
    public ResponseEntity<Object> getFriendsByLogin(@PathVariable String login) {
        return userService.getFriends(login);
    }

    @PostMapping("/review/leave")
    public ResponseEntity<Object> leaveReview(@RequestBody Review review) {
        return userService.leaveReview(review);
    }

    @GetMapping("/review/all/{login}")
    public ResponseEntity<Object> getUserReviews(@PathVariable String login) {
        return userService.getReviews(login);
    }

}
