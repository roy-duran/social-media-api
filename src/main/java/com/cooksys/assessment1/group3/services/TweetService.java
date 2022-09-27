package com.cooksys.assessment1.group3.services;

import java.util.List;

import com.cooksys.assessment1.group3.dtos.ContextDto;
import com.cooksys.assessment1.group3.dtos.CredentialsDto;
import com.cooksys.assessment1.group3.dtos.HashtagDto;
import com.cooksys.assessment1.group3.dtos.TweetRequestDto;
import com.cooksys.assessment1.group3.dtos.TweetResponseDto;
import com.cooksys.assessment1.group3.dtos.UserRequestDto;
import com.cooksys.assessment1.group3.dtos.UserResponseDto;
import com.cooksys.assessment1.group3.entities.Hashtag;
import com.cooksys.assessment1.group3.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	void createTweetLike(Long tweetId, CredentialsDto credentialsDto);

	TweetResponseDto createTweetReply(Long tweetId, TweetRequestDto tweetRequestDto);
	
	TweetResponseDto createTweetRepost(Long tweetId, CredentialsDto credentialsDto);

	TweetResponseDto getTweetById(Long tweetId);

	List<HashtagDto> getTweetTags(Long tweetId);

	List<UserResponseDto> getTweetLikes(Long tweetId);

	ContextDto getTweetContext(Long tweetId);

	List<TweetResponseDto> getTweetReplies(Long tweetId);

	List<TweetResponseDto> getTweetReposts(Long tweetId);

	List<UserResponseDto> getTweetMentions(Long tweetId);

	List<Hashtag> findHashtags(String content);

	List<User> findAttedUsers(String content);

	TweetResponseDto deleteTweet(Long tweetId);

	

}