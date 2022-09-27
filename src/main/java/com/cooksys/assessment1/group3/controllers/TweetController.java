package com.cooksys.assessment1.group3.controllers;

import com.cooksys.assessment1.group3.dtos.ContextDto;
import com.cooksys.assessment1.group3.dtos.CredentialsDto;
import com.cooksys.assessment1.group3.dtos.HashtagDto;
import com.cooksys.assessment1.group3.dtos.TweetRequestDto;
import com.cooksys.assessment1.group3.dtos.TweetResponseDto;
import com.cooksys.assessment1.group3.dtos.UserRequestDto;
import com.cooksys.assessment1.group3.dtos.UserResponseDto;
import com.cooksys.assessment1.group3.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

	private final TweetService tweetService;

	@GetMapping
	public List<TweetResponseDto> getAllTweets() {
		return tweetService.getAllTweets();
	}

	@PostMapping
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweet(tweetRequestDto);
	}

	@PostMapping("/{tweetId}/like")
	public void createTweetLike(@PathVariable Long tweetId, @RequestBody CredentialsDto credentialsDto) {
		tweetService.createTweetLike(tweetId, credentialsDto);
	}

	@PostMapping("/{tweetId}/reply")
	public TweetResponseDto createTweetReply(@PathVariable Long tweetId, @RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweetReply(tweetId, tweetRequestDto);
	}
	
	@PostMapping("/{tweetId}/repost")
	public TweetResponseDto createTweetRepost(@PathVariable Long tweetId, @RequestBody CredentialsDto credentialsDto) {
		return tweetService.createTweetRepost(tweetId, credentialsDto);
	}

	@GetMapping("/{tweetId}")
	public TweetResponseDto getTweetById(@PathVariable Long tweetId) {
		return tweetService.getTweetById(tweetId);
	}

	@GetMapping("/{tweetId}/tags")
	public List<HashtagDto> getTweetTags(@PathVariable Long tweetId) {
		return tweetService.getTweetTags(tweetId);
	}

	@GetMapping("/{tweetId}/likes")
	public List<UserResponseDto> getTweetLikes(@PathVariable Long tweetId) {
		return tweetService.getTweetLikes(tweetId);
	}

	@GetMapping("/{tweetId}/context")
	public ContextDto getTweetContext(@PathVariable Long tweetId) {
		return tweetService.getTweetContext(tweetId);
	}

	@GetMapping("/{tweetId}/replies")
	public List<TweetResponseDto> getTweetReplies(@PathVariable Long tweetId) {
		return tweetService.getTweetReplies(tweetId);
	}

	@GetMapping("/{tweetId}/reposts")
	public List<TweetResponseDto> getTweetReposts(@PathVariable Long tweetId) {
		return tweetService.getTweetReposts(tweetId);
	}

	@GetMapping("/{tweetId}/mentions")
	public List<UserResponseDto> getTweetMentions(@PathVariable Long tweetId) {
		return tweetService.getTweetMentions(tweetId);
	}
	
	@DeleteMapping("/{tweetId}")
	public TweetResponseDto deleteTweet(@PathVariable Long tweetId) {
		return tweetService.deleteTweet(tweetId);
	}

}
