package com.cooksys.assessment1.group3.dtos;

import com.cooksys.assessment1.group3.entities.embeddable.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class UserResponseDto {

    private String username;

    private Profile profile;

    private Timestamp joined;

}
