package com.cooksys.assessment1.group3.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.assessment1.group3.dtos.HashtagDto;
import com.cooksys.assessment1.group3.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    HashtagDto entityToDto(Hashtag hashtag);

    List<HashtagDto> entitiesToDtos(List<Hashtag> hashtags);

    Hashtag dtoToEntity(HashtagDto dto);

}
