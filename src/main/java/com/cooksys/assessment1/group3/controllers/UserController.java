package com.cooksys.assessment1.group3.controllers;

import com.cooksys.assessment1.group3.dtos.*;
import com.cooksys.assessment1.group3.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllNonDeletedUsers() {
        return userService.getAllNonDeletedUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @GetMapping("/@{username}")
    public UserResponseDto getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PatchMapping("/@{username}")
    public UserResponseDto updateProfile(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateProfile(username, userRequestDto);
    }

    @DeleteMapping("/@{username}")
    public UserResponseDto deleteProfile(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
        return userService.deleteProfile(username, userRequestDto);
    }

    // no data is sent back
    @PostMapping("/@{username}/follow")
    public ErrorDto followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
        return userService.followUser(username, credentialsDto);
    }

    @PostMapping("/@{username}/unfollow")
    public ErrorDto unFollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
        return userService.unFollowUser(username, credentialsDto);
    }

    @GetMapping("/@{username}/feed")
    public List<TweetResponseDto> getUserFeed(@PathVariable String username) {
        return userService.getUserFeed(username);
    }

    @GetMapping("/@{username}/tweets")
    public List<TweetResponseDto> getAllTweetsFromUser(@PathVariable String username) {
        return userService.getAllTweetsFromUser(username);
    }

    // need regex to find the @username in the content of the tweet
    @GetMapping("/@{username}/mentions")
    public List<TweetResponseDto> getAllTweetMentionsOfUser(@PathVariable String username) {
        return userService.getAllTweetMentionsOfUser(username);
    }

    @GetMapping("/@{username}/followers")
    public List<UserResponseDto> getFollowersOfUser(@PathVariable String username) {
        return userService.getFollowersOfUser(username);
    }

    @GetMapping("/@{username}/following")
    public List<UserResponseDto> getUsersOneIsFollowing(@PathVariable String username) {
        return userService.getUsersOneIsFollowing(username);
    }

}
