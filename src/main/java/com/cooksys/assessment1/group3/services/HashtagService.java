package com.cooksys.assessment1.group3.services;

import java.util.List;

import com.cooksys.assessment1.group3.dtos.HashtagDto;
import com.cooksys.assessment1.group3.dtos.TweetResponseDto;

public interface HashtagService {

    List<HashtagDto> getAllHashtags();

    List<TweetResponseDto> getHashtags(String label);

}
