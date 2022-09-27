package com.cooksys.assessment1.group3.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetRequestDto {
    private String content;
    private CredentialsDto credentials;
}
