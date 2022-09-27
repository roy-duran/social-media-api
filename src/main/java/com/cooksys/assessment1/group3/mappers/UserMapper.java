package com.cooksys.assessment1.group3.mappers;

import com.cooksys.assessment1.group3.dtos.UserRequestDto;
import com.cooksys.assessment1.group3.dtos.UserResponseDto;
import com.cooksys.assessment1.group3.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { CredentialsMapper.class, ProfileMapper.class })
public interface UserMapper {

    @Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToDto(User user);

    List<UserResponseDto> entitiesToDtos(List<User> entities);

    User dtoToEntity(UserRequestDto userRequestDto);
}
