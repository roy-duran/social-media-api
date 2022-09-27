package com.cooksys.assessment1.group3.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cooksys.assessment1.group3.entities.Hashtag;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cooksys.assessment1.group3.dtos.HashtagDto;
import com.cooksys.assessment1.group3.dtos.TweetResponseDto;
import com.cooksys.assessment1.group3.entities.Tweet;
import com.cooksys.assessment1.group3.exceptions.BadRequestException;
import com.cooksys.assessment1.group3.exceptions.NotFoundException;
import com.cooksys.assessment1.group3.mappers.HashtagMapper;
import com.cooksys.assessment1.group3.mappers.TweetMapper;
import com.cooksys.assessment1.group3.repositories.HashtagRepository;
import com.cooksys.assessment1.group3.repositories.TweetRepository;
import com.cooksys.assessment1.group3.services.HashtagService;
import com.cooksys.assessment1.group3.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;
	private final TweetService tweetService;

	@Override
	public List<HashtagDto> getAllHashtags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

	@Override
	public List<TweetResponseDto> getHashtags(String label) {
		// Logic works, but the returned Dto is an empty list according to the test
		if (label == null || label.isBlank()) {
			throw new BadRequestException("Bad Request: Given label is empty/null");
		}
		// String regexPattern = "(#\\w+)";
		// Pattern pattern = Pattern.compile(regexPattern);
		List<Tweet> allTweets = tweetRepository.findAll();
		List<Tweet> taggedTweets = new ArrayList<>();
		for (Tweet tweet : allTweets) {
			if (tweet.getContent() != null && !tweet.isDeleted()) {
				// Matcher matcher = pattern.matcher(tweet.getContent());
				for (Hashtag h : tweetService.findHashtags(tweet.getContent())) {
					if (h.getLabel().equals(label)) {
						taggedTweets.add(tweet);
					}
				}
			}
		}
		if (taggedTweets.isEmpty()) {
			throw new NotFoundException("No tweet with hashtag '#" + label + "' exists.");
		}
		return tweetMapper.entitiesToDtos(taggedTweets);
	}

}
