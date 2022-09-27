package com.cooksys.assessment1.group3.dtos;

import java.util.List;

import com.cooksys.assessment1.group3.entities.Tweet;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto {

    private TweetResponseDto target;

    private List<TweetResponseDto> before;

    private List<TweetResponseDto> after;

}
