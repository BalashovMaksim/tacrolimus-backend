package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.CreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.dto.UpdateDto;
import com.tacrolimus.backend.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    PersonReadDto create(CreateDto createDto);
    PersonReadDto mapToPersonReadDto(Person person);
    List<PersonReadDto> getAll();
    List<PersonReadDto> getTodayBirthdays();
    List<PersonReadDto> getNextBirthdays();
    PersonReadDto getById(UUID id);
    PersonReadDto update(UUID id, UpdateDto updateDto);
    void deleteById(UUID id);
}
