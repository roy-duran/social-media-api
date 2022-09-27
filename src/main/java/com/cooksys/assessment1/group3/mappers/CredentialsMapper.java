package com.cooksys.assessment1.group3.mappers;

import org.mapstruct.Mapper;

import com.cooksys.assessment1.group3.dtos.CredentialsDto;
import com.cooksys.assessment1.group3.entities.embeddable.Credentials;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    CredentialsDto entityToDto(Credentials credentials);

    Credentials dtoToEntity(CredentialsDto dto);

}
