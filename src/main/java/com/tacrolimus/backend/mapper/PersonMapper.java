package com.tacrolimus.backend.mapper;

import com.tacrolimus.backend.dto.PersonCreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.dto.PersonUpdateDto;
import com.tacrolimus.backend.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonCreateDto personCreateDto);
    PersonReadDto toDto(Person person);
    void updateEntity(PersonUpdateDto personUpdateDto, @MappingTarget Person person);
}
