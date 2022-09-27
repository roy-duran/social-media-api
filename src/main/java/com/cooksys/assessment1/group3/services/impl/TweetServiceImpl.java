package com.cooksys.assessment1.group3.services.impl;

import com.cooksys.assessment1.group3.dtos.*;
import com.cooksys.assessment1.group3.entities.Hashtag;
import com.cooksys.assessment1.group3.entities.Tweet;
import com.cooksys.assessment1.group3.entities.User;
import com.cooksys.assessment1.group3.exceptions.BadRequestException;
import com.cooksys.assessment1.group3.exceptions.NotAuthorizedException;
import com.cooksys.assessment1.group3.exceptions.NotFoundException;
import com.cooksys.assessment1.group3.mappers.CredentialsMapper;
import com.cooksys.assessment1.group3.mappers.HashtagMapper;
import com.cooksys.assessment1.group3.mappers.TweetMapper;
import com.cooksys.assessment1.group3.mappers.UserMapper;
import com.cooksys.assessment1.group3.repositories.HashtagRepository;
import com.cooksys.assessment1.group3.repositories.TweetRepository;
import com.cooksys.assessment1.group3.repositories.UserRepository;
import com.cooksys.assessment1.group3.services.TweetService;
import com.cooksys.assessment1.group3.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	private final CredentialsMapper credentialsMapper;
	private final UserService userService;

	private Tweet getTweet(Long tweetId) {
		Optional<Tweet> optionalTweet = tweetRepository.findById(tweetId);
		if (optionalTweet.isEmpty()) {
			throw new NotFoundException("No tweet with Id '" + tweetId + "' found.");
		}
		if (optionalTweet.get().isDeleted()) {
			throw new BadRequestException("Tweet with id '" + tweetId + "' is flagged as deleted.");
		}
		return optionalTweet.get();
	}

	@Override
	public void createTweetLike(Long tweetId, CredentialsDto credentialsDto) {
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(credentialsDto.getUsername());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.getCredentials().equals(credentialsMapper.dtoToEntity(credentialsDto))) {
				//Get tweet entity
				Tweet likedTweet = getTweet(tweetId);
				//Check if user has already liked tweet
				if (likedTweet.getLikedBy().contains(user)) {
					return;
				}
				//handle user info
				List<Tweet> likedTweets =  user.getLikedTweets();
				likedTweets.add(likedTweet);
				user.setLikedTweets(likedTweets);
				//handle tweet info
				List<User> tempLikedBy = likedTweet.getLikedBy();
				tempLikedBy.add(user);
				likedTweet.setLikedBy(tempLikedBy);
				//saves
				userRepository.saveAndFlush(user);
				tweetRepository.saveAndFlush(likedTweet);
			} else {
				throw new NotAuthorizedException("Users password doesn't match username");
			}
		} else {
			throw new NotFoundException("User with given credentials not found");
		}
	}
	
	@Override
	public TweetResponseDto createTweetReply(Long tweetId, TweetRequestDto tweetRequestDto) {
		Optional<Tweet> optionalReplyTo = tweetRepository.findById(tweetId);
		if (optionalReplyTo.isPresent()) {
			//Gets both tweets
			Tweet tweetReplyTo = optionalReplyTo.get();
			Tweet replyTweet = tweetRepository.findById(((long) createTweet(tweetRequestDto).getId())).get();
			//sets replying tweet info
			replyTweet.setInReplyTo(tweetReplyTo);
			//sets replied tweet info
			List<Tweet> replies = tweetReplyTo.getReplies();
			replies.add(replyTweet);
			tweetReplyTo.setReplies(replies);
			//Saves tweets
			tweetRepository.saveAndFlush(tweetReplyTo);
			tweetRepository.saveAndFlush(replyTweet);
			return tweetMapper.entityToDto(replyTweet);
		}
		throw new NotFoundException("Tweet of id" + tweetId + "not found");
	}
	
	@Override
	public TweetResponseDto createTweetRepost(Long tweetId, CredentialsDto credentialsDto) {
		Tweet tweet = getTweet(tweetId);
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(credentialsDto.getUsername());
		//check if user is valid
		if (optionalUser.isPresent()) {
			//get user
			User user = optionalUser.get();
			//handle new tweet info
			Tweet respostTweet = new Tweet();
			respostTweet.setAuthor(user);
			respostTweet.setRepostOf(tweet);
			//handle original tweet info
			List<Tweet> repostsList = tweet.getReposts();
			repostsList.add(respostTweet);
			tweet.setReposts(repostsList);
			//save and return
			tweetRepository.saveAndFlush(tweet);
			return tweetMapper.entityToDto(tweetRepository.saveAndFlush(respostTweet));
		} else {
			throw new NotFoundException("User not found");
		}
	}	
	

	@Override
	public List<TweetResponseDto> getAllTweets() {
		List<TweetResponseDto> listToReturn = tweetMapper.entitiesToDtos(tweetRepository.findAll());
		// Checking to see if there are tweets
		if (!listToReturn.isEmpty()) {
			// Removing deleted tweets from list
			listToReturn.removeIf(TweetResponseDto::isDeleted);
			// Sort list by posted timestamp
			listToReturn.sort(new Comparator<TweetResponseDto>() {
				@Override
				public int compare(TweetResponseDto o1, TweetResponseDto o2) {
					return o1.getPosted().compareTo(o2.getPosted());
				}
			});
		}
		return listToReturn;
	}

	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		//Checks content to make sure there is any
		try {
			if (tweetRequestDto.getContent() == null || tweetRequestDto.getContent().length() < 1 ) {
				throw new BadRequestException("Tweet must have a content body");
			}
		} catch (Error e) {
			throw new BadRequestException("Tweet must have a content body");
		}
		// First checks the credentials to see if they match an existing user
		boolean userFound = false;
		User poster = null;
		List<User> allUsers = userRepository.findAll();
		for (User u : allUsers) {
			if (u.getCredentials().equals(credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials()))) {
				poster = u;
				userFound = true;
			}
		}
		if (!userFound) {
			throw new NotFoundException("Credentials do not match an active user.");
		}
		Tweet tempTweetEntity = tweetMapper.dtoToEntity(tweetRequestDto);
		// Connects tweet with user
		tempTweetEntity.setAuthor(poster);
		tweetRepository.saveAndFlush(tempTweetEntity);

		List<Tweet> posterTweets = poster.getTweets();
		posterTweets.add(tempTweetEntity);

		poster.setTweets(posterTweets);
		userRepository.saveAndFlush(poster);
		// Handles mentions of other users with @s in content
		List<User> mentionedUsers = findAttedUsers(tempTweetEntity.getContent());
		if (mentionedUsers.size() > 0) {
			for (User u : mentionedUsers) {
				List<Tweet> tempMentions = u.getUserMentions();
				tempMentions.add(tempTweetEntity);
				u.setUserMentions(tempMentions);
				userRepository.saveAndFlush(u);
			}
			tempTweetEntity.setTweetMentions(mentionedUsers);
		}
		// Handles hashtags in content
		List<Hashtag> usedHashtags = findHashtags(tempTweetEntity.getContent());
		if (usedHashtags.size() > 0) {
			for (Hashtag h : usedHashtags) {
				// Hashtag join half
				List<Tweet> tempTweetsUsingHashtag = h.getTweets();
				tempTweetsUsingHashtag.add(tempTweetEntity);
				h.setTweets(tempTweetsUsingHashtag);
				hashtagRepository.saveAndFlush(h);
				// Tweet join half
				List<Hashtag> tempUsingHashtags = tempTweetEntity.getHashtags();
				tempUsingHashtags.add(h);
				tempTweetEntity.setHashtags(tempUsingHashtags);
			}
		}
		// Save and finish
		tweetRepository.saveAndFlush(tempTweetEntity);
		return tweetMapper.entityToDto(tempTweetEntity);
	}
	
	public Tweet createTweetAsTweet(TweetRequestDto tweetRequestDto) {
		TweetResponseDto dto = createTweet(tweetRequestDto);
		return tweetMapper.responseDtoToEntity(dto);
	}

	
	@Override
	public List<Hashtag> findHashtags(String content) {

		List<Hashtag> hashtags = new ArrayList<>();
		List<Integer> indexes = new ArrayList<>();
		for (int i = 0; i < content.length(); i++) {
			if (content.charAt(i) == '#') {
				indexes.add(i);
			}
		}
		for (int i = 0; i < indexes.size(); i++) {
			int endIndex = content.indexOf(" ", indexes.get(i));
			if (endIndex == -1) {
				endIndex = content.length();
			}
			String hashtagString = content.substring(indexes.get(i) + 1, endIndex);
			Optional<Hashtag> optionalHashtag = hashtagRepository.findHashtagByLabel(hashtagString);
			if (optionalHashtag.isPresent()) {
				hashtags.add(optionalHashtag.get());
			} else {
				Hashtag newHashtag = new Hashtag();
				newHashtag.setLabel(hashtagString);
				hashtagRepository.saveAndFlush(newHashtag);
				hashtags.add(newHashtag);
			}
		}
		return hashtags;
	}

	@Override
	public List<User> findAttedUsers(String content) {
//		List<User> users = new ArrayList<>();
//		String regex = "@((?=.[A-Za-z\\d])[\\w_]+)";
//		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(content);
//		while (matcher.find()) {
//			String username = matcher.group(1);
//			Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
//			optionalUser.ifPresent(users::add);
//		}

		List<User> users = new ArrayList<>();
		List<Integer> indexes = new ArrayList<>();
		for (int i = 0; i < content.length(); i++) {
			if (content.charAt(i) == '@') {
				indexes.add(i);
			}
		}
		for (int i = 0; i < indexes.size(); i++) {
			int endIndex = content.indexOf(" ", indexes.get(i));
			if (endIndex == -1) {
				endIndex = content.length();
			}
			String userString = content.substring(indexes.get(i) + 1, endIndex);
			Optional<User> optionalUser = userRepository.findByCredentialsUsername(userString);
			if (optionalUser.isPresent()) {
				users.add(optionalUser.get());
			}
		}
		return users;
	}

	@Override
	public TweetResponseDto getTweetById(Long tweetId) {
		Tweet tweet = getTweet(tweetId);
		if (tweet.isDeleted()) {
			throw new BadRequestException("Id references a deleted tweet");
		}
		return tweetMapper.entityToDto(tweet);
	}

	@Override
	public List<HashtagDto> getTweetTags(Long tweetId) {
		Tweet tweet = getTweet(tweetId);
		if (tweet.isDeleted()) {
			throw new BadRequestException("Id references a deleted tweet");
		}
		List<Hashtag> hashtags = tweet.getHashtags();
		return hashtagMapper.entitiesToDtos(hashtags);
	}

	@Override
	public List<UserResponseDto> getTweetLikes(Long tweetId) {
		Tweet tweet = getTweet(tweetId);
		if (tweet.isDeleted()) {
			throw new BadRequestException("Id references a deleted tweet");
		}
		return userMapper.entitiesToDtos(tweet.getLikedBy());
	}

	@Override
	public ContextDto getTweetContext(Long tweetId) {
		Tweet tweet = getTweet(tweetId);
		if (tweet.isDeleted()) {
			throw new BadRequestException("Id references a deleted tweet");
		}
		ContextDto currentContext = new ContextDto();
		currentContext.setTarget(tweetMapper.entityToDto(tweet));
		currentContext.setAfter(tweetMapper.entitiesToDtos(tweet.getReplies()));
		Tweet currentTweet = tweet;
		List<TweetResponseDto> beforeList = new ArrayList<>();
		while (currentTweet.getInReplyTo() != null) {
			beforeList.add(tweetMapper.entityToDto(currentTweet.getInReplyTo()));
			currentTweet = currentTweet.getInReplyTo();
		}
		currentContext.setBefore(beforeList);
		return currentContext;
	}

	@Override
	public List<TweetResponseDto> getTweetReplies(Long tweetId) {
		Tweet tweetRepliesOf = tweetRepository.getReferenceById(tweetId);
		List<Tweet> tweetReplies = new ArrayList<>();
		for (Tweet tweet : tweetRepository.findAll()) {
			if (tweet.getInReplyTo() == tweetRepliesOf) {
				tweetReplies.add(tweet);
			}
		}
		return tweetMapper.entitiesToDtos(tweetReplies);
	}

	@Override
	public List<TweetResponseDto> getTweetReposts(Long tweetId) {
		Tweet tweet = getTweet(tweetId);
		if (tweet.isDeleted()) {
			throw new BadRequestException("Id references a deleted tweet");
		}
		return tweetMapper.entitiesToDtos(tweet.getReposts());
	}

	@Override
	public List<UserResponseDto> getTweetMentions(Long tweetId) {
		Tweet tweet = getTweet(tweetId);
		if (tweet.isDeleted()) {
			throw new BadRequestException("Id references a deleted tweet");
		}
		return userMapper.entitiesToDtos(tweet.getTweetMentions());
	}

	@Override
	public TweetResponseDto deleteTweet(Long tweetId) {
		Tweet tweet = getTweet(tweetId);
		if (tweet.isDeleted()) {
			throw new BadRequestException("Id references an already deleted tweet");
		}
		tweet.setDeleted(true);
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
	}


}
