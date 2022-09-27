package com.cooksys.assessment1.group3.mappers;

import com.cooksys.assessment1.group3.dtos.TweetRequestDto;
import com.cooksys.assessment1.group3.dtos.TweetResponseDto;
import com.cooksys.assessment1.group3.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class,  CredentialsMapper.class})
public interface TweetMapper {

    TweetResponseDto entityToDto(Tweet tweet);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);

    Tweet dtoToEntity(TweetRequestDto dto);

	TweetRequestDto entityToRequestDto(Tweet tweet);
	
	Tweet responseDtoToEntity(TweetResponseDto tweetResponseDto);

    List<Tweet> dtosToEntities(List<TweetResponseDto> allTweetsFromUser);
}
