package com.cooksys.assessment1.group3.mappers;

import org.mapstruct.Mapper;

import com.cooksys.assessment1.group3.dtos.ProfileDto;
import com.cooksys.assessment1.group3.entities.embeddable.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto entityToDto(Profile profile);

    Profile dtoToEntity(ProfileDto profile);

}
