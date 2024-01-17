package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.PersonCreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.dto.PersonUpdateDto;
import com.tacrolimus.backend.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    PersonReadDto create(PersonCreateDto personCreateDto);
    PersonReadDto mapToPersonReadDto(Person person);
    List<PersonReadDto> getAll();
    List<PersonReadDto> getTodayBirthdays();
    List<PersonReadDto> getNextBirthdays();
    PersonReadDto getById(UUID id);
    PersonReadDto update(UUID id, PersonUpdateDto personUpdateDto);
    void deleteById(UUID id);
}
