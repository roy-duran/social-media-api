package com.cooksys.assessment1.group3.services;

import com.cooksys.assessment1.group3.dtos.*;
import com.cooksys.assessment1.group3.entities.User;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllNonDeletedUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto getUser(String username);

    UserResponseDto updateProfile(String username, UserRequestDto userRequestDto);

    UserResponseDto deleteProfile(String username, UserRequestDto userRequestDto);

    ErrorDto followUser(String username, CredentialsDto credentialsDto);

    ErrorDto unFollowUser(String username, CredentialsDto credentialsDto);

    List<TweetResponseDto> getUserFeed(String username);

    List<TweetResponseDto> getAllTweetsFromUser(String username);

    List<TweetResponseDto> getAllTweetMentionsOfUser(String username);

    List<UserResponseDto> getFollowersOfUser(String username);

    List<UserResponseDto> getUsersOneIsFollowing(String username);
    User getAUserByUsername(String username);

}
