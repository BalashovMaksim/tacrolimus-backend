package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.PersonCreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.dto.PersonUpdateDto;
import com.tacrolimus.backend.exception.PersonNotFoundException;
import com.tacrolimus.backend.mapper.PersonMapper;
import com.tacrolimus.backend.model.Person;
import com.tacrolimus.backend.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Transactional
    public PersonReadDto create(PersonCreateDto personCreateDto) {
        Person person = personMapper.toEntity(personCreateDto);
        Person savedPerson = personRepository.save(person);
        return personMapper.toDto(savedPerson);
    }

    @Transactional
    public List<PersonReadDto> getAll() {
        List<Person> persons = personRepository.findAll();
        return persons.stream()
                .filter(p -> !p.getIsDeleted())
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PersonReadDto> getTodayBirthdays() {
        LocalDate today = LocalDate.now();
        List<Person> persons = personRepository.findAll();

        return persons.stream()
                .filter(person -> !person.getIsDeleted())
                .filter(person -> person.getBirthday() != null)
                .filter(person -> person.getBirthday().equals(today.withYear(person.getBirthday().getYear())))
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PersonReadDto> getNextBirthdays() {
        LocalDate today = LocalDate.now();
        LocalDate plusThirtyDays = today.plusDays(30);
        List<Person> persons = personRepository.findAllByBirthdayBetween(today.getMonthValue(), today.getDayOfMonth(), plusThirtyDays.getMonthValue(), plusThirtyDays.getDayOfMonth());
        return persons.stream()
                .filter(p -> !p.getIsDeleted())
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PersonReadDto getById(UUID id) {
        Person person = findById(id);

        return personMapper.toDto(person);
    }

    @Transactional
    public PersonReadDto update(UUID id, PersonUpdateDto personUpdateDto) {
        Person person = findById(id);

        personMapper.updateEntity(personUpdateDto, person);
        personRepository.save(person);

        return personMapper.toDto(person);
    }

    @Transactional
    public void deleteById(UUID id) {
        Person person = findById(id);

        person.setIsDeleted(true);
        personRepository.save(person);
    }

    public Person findById(UUID id){
        return personRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
