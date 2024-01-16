package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.CreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.dto.UpdateDto;
import com.tacrolimus.backend.model.Person;
import com.tacrolimus.backend.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public PersonReadDto create(CreateDto createDto) {
        Person person = Person.builder()
                .firstName(createDto.getFirstName())
                .lastName(createDto.getLastName())
                .birthday(createDto.getBirthday())
                .transplantationDate(createDto.getTransplantationDate())
                .organ(createDto.getOrgan())
                .build();
        Person savedPerson = personRepository.save(person);
        return mapToPersonReadDto(savedPerson);
    }

    @Override
    @Transactional
    public List<PersonReadDto> getAll() {
        List<Person> persons = personRepository.findAll();
        return persons.stream()
                .filter(p -> !p.isDeleted())
                .map(this::mapToPersonReadDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PersonReadDto> getTodayBirthdays() {
        LocalDate today = LocalDate.now();
        List<Person> persons = personRepository.findAll();
        List<PersonReadDto> todayBirthdays = new ArrayList<>();
        for (Person person: persons){
            if(person.getBirthday().getMonth() == today.getMonth() && person.getBirthday().getDayOfMonth() == today.getDayOfMonth() && !person.isDeleted()){
                todayBirthdays.add(mapToPersonReadDto(person));
            }
        }
        return todayBirthdays;
    }

    @Override
    @Transactional
    public List<PersonReadDto> getNextBirthdays() {
        LocalDate today = LocalDate.now();
        LocalDate plusThirtyDays = today.plusDays(30);
        List<Person> persons = personRepository.findAllByBirthdayBetween(today.getMonthValue(), today.getDayOfMonth(), plusThirtyDays.getMonthValue(), plusThirtyDays.getDayOfMonth());
        return persons.stream()
                .filter(person -> !person.isDeleted())
                .map(this::mapToPersonReadDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PersonReadDto getById(UUID id) {
        Person person = personRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Person with id " + id + " not found or deleted"));

        return mapToPersonReadDto(person);
    }

    @Override
    @Transactional
    public PersonReadDto update(UUID id, UpdateDto updateDto) {
        Person person = personRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Person with id " + id + " not found or deleted"));

        person.setFirstName(updateDto.getFirstName());
        person.setLastName(updateDto.getLastName());
        person.setBirthday(updateDto.getBirthday());
        person.setTransplantationDate(updateDto.getTransplantationDate());
        person.setOrgan(updateDto.getOrgan());
        person.setUpdatedAt(LocalDate.now());

        personRepository.save(person);

        return mapToPersonReadDto(person);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        Person person = personRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Person with id " + id + " not found or deleted"));

        person.setDeleted(true);
        personRepository.save(person);
    }

    public PersonReadDto mapToPersonReadDto(Person person) {
        return PersonReadDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthday(person.getBirthday())
                .transplantationDate(person.getTransplantationDate())
                .organ(person.getOrgan())
                .build();
    }
}
