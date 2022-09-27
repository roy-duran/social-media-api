package com.cooksys.assessment1.group3.services.impl;

import com.cooksys.assessment1.group3.dtos.*;
import com.cooksys.assessment1.group3.entities.Tweet;
import com.cooksys.assessment1.group3.entities.User;
import com.cooksys.assessment1.group3.entities.embeddable.Credentials;
import com.cooksys.assessment1.group3.exceptions.BadRequestException;
import com.cooksys.assessment1.group3.exceptions.NotFoundException;
import com.cooksys.assessment1.group3.mappers.CredentialsMapper;
import com.cooksys.assessment1.group3.mappers.TweetMapper;
import com.cooksys.assessment1.group3.mappers.UserMapper;
import com.cooksys.assessment1.group3.repositories.TweetRepository;
import com.cooksys.assessment1.group3.repositories.UserRepository;
import com.cooksys.assessment1.group3.services.UserService;
import com.cooksys.assessment1.group3.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final ValidateService validateService;

	private final CredentialsMapper credentialsMapper;

	private void checkCredentials(User user, Credentials credentials) {
		if ((!user.getCredentials().equals(credentials)) || (credentials.getUsername() == null) || (credentials.getPassword() == null)) {
			throw new BadRequestException("Invalid credentials");
		}
	}

	private boolean credentialsPassed(User userToUpdate, User updates) {
		if (updates.getCredentials().getUsername().equals(userToUpdate.getCredentials().getUsername())
				&& updates.getCredentials().getPassword().equals(userToUpdate.getCredentials().getPassword())) {
			return true;
		}
		throw new NotFoundException("User with these credentials does not exist, please check username/password");
	}

	@Override
	public User getAUserByUsername(String username) {
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("No user with name '" + username + "' found.");
		}
		if (optionalUser.get().isDeleted()) {
			throw new BadRequestException("User '" + username + "' is flagged as deactivated.");
		}
		return optionalUser.get();
	}

	// Finished
	@Override
	public List<UserResponseDto> getAllNonDeletedUsers() {
		List<User> listOfUsers = userRepository.findAll();
		listOfUsers.removeIf(User::isDeleted);
		return userMapper.entitiesToDtos(listOfUsers);
	}

	// Finished
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		User userToCreate = userMapper.dtoToEntity(userRequestDto);
		List<User> deletedUsers = userRepository.findAll();
		deletedUsers.removeIf(user -> !user.isDeleted());
		for (User deletedUser : deletedUsers) {
			if (deletedUser.getCredentials().equals(userToCreate.getCredentials())) {
				deletedUser.setDeleted(false);
				userRepository.saveAndFlush(deletedUser);
				return userMapper.entityToDto(deletedUser);
			}
		}
		try {
			if (userToCreate.getCredentials().getUsername() == null
					|| userToCreate.getCredentials().getPassword() == null
					|| userToCreate.getProfile().getEmail() == null) {
				throw new BadRequestException("Please provide all required fields");
			}
		} catch (NullPointerException e) {
			throw new BadRequestException("Please provide all required fields.");
		}

		if (!validateService.validateUsernameAvailable(userToCreate.getCredentials().getUsername())) {
			throw new BadRequestException("This username is already taken");
		} else if (userRequestDto.getCredentials() == null || userToCreate.getProfile() == null) {
			throw new BadRequestException("Please Provide Credentials and Profile");
		}

		return userMapper.entityToDto(userRepository.saveAndFlush(userToCreate));
	}

	// Finished
	@Override
	public UserResponseDto getUser(String username) {
		User userToGet = getAUserByUsername(username);
		return userMapper.entityToDto(userToGet);
	}

	// Finished
	@Override
	public UserResponseDto updateProfile(String username, UserRequestDto userRequestDto) {
		validateService.validateUsernameExists(username);
		User userToUpdate = getAUserByUsername(username);
		User updates = userMapper.dtoToEntity(userRequestDto);
		if (updates.getCredentials() == null) {
			throw new BadRequestException("Please provide credentials");
		} else
			checkCredentials(userToUpdate, updates.getCredentials());
		if (updates.getProfile() == null ){
			throw new BadRequestException("Please provide something to update");
		}
		if (updates.getProfile().getFirstName() != null)
			userToUpdate.getProfile().setFirstName(updates.getProfile().getFirstName());
		if (updates.getProfile().getLastName() != null)
			userToUpdate.getProfile().setLastName(updates.getProfile().getLastName());
		if (updates.getProfile().getEmail() != null)
			userToUpdate.getProfile().setEmail(updates.getProfile().getEmail());
		if (updates.getProfile().getPhone() != null)
			userToUpdate.getProfile().setPhone(updates.getProfile().getPhone());
		return userMapper.entityToDto(userRepository.saveAndFlush(userToUpdate));
	}

	// Finished
	@Override
	public UserResponseDto deleteProfile(String username, UserRequestDto userRequestDto) {
		validateService.validateUsernameExists(username);
		User userToDelete = getAUserByUsername(username);
		userToDelete.setDeleted(true);
		userRepository.saveAndFlush(userToDelete);
		return userMapper.entityToDto(userToDelete);
	}

	@Override
	public ErrorDto followUser(String username, CredentialsDto credentialsDto) {
		if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
			throw new BadRequestException("Please provide credentials");
		}
		User iWantToFollow = getAUserByUsername(credentialsDto.getUsername());
		User accountToFollow = getAUserByUsername(username);
		Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
		checkCredentials(iWantToFollow, credentials);
		if (accountToFollow.getFollowers().contains(iWantToFollow)) {
			throw new BadRequestException("You already follow this user");
		} else {
			iWantToFollow.getFollowing().add(accountToFollow);
			accountToFollow.getFollowers().add(iWantToFollow);
			userRepository.saveAndFlush(iWantToFollow);
			userRepository.saveAndFlush(accountToFollow);
		}
		return null;

	}

	@Override
	public ErrorDto unFollowUser(String username, CredentialsDto credentialsDto) {
		if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
			throw new BadRequestException("Please provide credentials");
		}
		User iWantToUnfollow = getAUserByUsername(credentialsDto.getUsername());
		User accountToUnfollow = getAUserByUsername(username);
		Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
		checkCredentials(iWantToUnfollow, credentials);
		if (!accountToUnfollow.getFollowers().contains(iWantToUnfollow)) {
			throw new BadRequestException("You already unfollowed this user");
		} else {
			iWantToUnfollow.getFollowing().remove(accountToUnfollow);
			accountToUnfollow.getFollowers().remove(iWantToUnfollow);
			userRepository.saveAndFlush(iWantToUnfollow);
			userRepository.saveAndFlush(accountToUnfollow);
		}
		return null;
	}

	@Override
	public List<TweetResponseDto> getUserFeed(String username) {
		// Needs more work
		User user = getAUserByUsername(username);
		List<TweetResponseDto> feedTweets = getAllTweetsFromUser(user.getCredentials().getUsername());
//		for (TweetResponseDto tweet : feedTweets) {
//			tweet.setAuthor(tweet.getAuthor());
//		}
		List<UserResponseDto> followedUsers = getUsersOneIsFollowing(user.getCredentials().getUsername());

		//go thru list of users
		//get their tweets in a list
		//order that list + my feed in chronological order
		for (UserResponseDto followedUser : followedUsers) {
			List<TweetResponseDto> thisUsersTweets = getAllTweetsFromUser(followedUser.getUsername());
			feedTweets.addAll(thisUsersTweets);
		}
			feedTweets.sort(new Comparator<TweetResponseDto>() {
				@Override
				public int compare(TweetResponseDto o1, TweetResponseDto o2) {
					return o1.getPosted().compareTo(o2.getPosted());
				}
			});
		return feedTweets;
	}

	@Override
	public List<TweetResponseDto> getAllTweetsFromUser(String username) {
		// Logic coded, needs testing
		User user = getAUserByUsername(username);
		List<Tweet> userTweets = user.getTweets();
		userTweets.removeIf(Tweet::isDeleted);
		userTweets.sort(new Comparator<Tweet>() {
			@Override
			public int compare(Tweet o1, Tweet o2) {
				return o1.getPosted().compareTo(o2.getPosted());
			}
		});
		return tweetMapper.entitiesToDtos(userTweets);
	}

	@Override
	public List<TweetResponseDto> getAllTweetMentionsOfUser(String username) {
		// Logic coded, needs testing
		User user = getAUserByUsername(username);
		List<Tweet> userMentions = user.getUserMentions();
		userMentions.removeIf(Tweet::isDeleted);
		userMentions.sort(new Comparator<Tweet>() {
			@Override
			public int compare(Tweet o1, Tweet o2) {
				return o1.getPosted().compareTo(o2.getPosted());
			}
		});

		return tweetMapper.entitiesToDtos(userMentions);
	}

	@Override
	public List<UserResponseDto> getFollowersOfUser(String username) {
		// Logic coded, needs testing
		User user = getAUserByUsername(username);
		List<User> followers = user.getFollowers();
		return userMapper.entitiesToDtos(followers);
	}

	@Override
	public List<UserResponseDto> getUsersOneIsFollowing(String username) {
		// Logic coded, needs testing
		User user = getAUserByUsername(username);
		List<User> following = user.getFollowing();
		return userMapper.entitiesToDtos(following);
	}
}
