package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.PersonCreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.dto.PersonUpdateDto;
import com.tacrolimus.backend.enu.OrganEnum;
import com.tacrolimus.backend.mapper.PersonMapper;
import com.tacrolimus.backend.model.Person;
import com.tacrolimus.backend.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        personService = new PersonService(personRepository, personMapper);
    }

    @Test
    public void create_ShouldCreatePersonSuccessfully() {
        PersonCreateDto personCreateDto = PersonCreateDto.builder()
                .firstName("Maksim")
                .lastName("Balashov")
                .birthday(LocalDate.now())
                .transplantationDate(LocalDate.now())
                .organ(Collections.singletonList(com.tacrolimus.backend.enu.OrganEnum.HEART))
                .build();

        Person person = Person.builder()
                .firstName(personCreateDto.getFirstName())
                .lastName(personCreateDto.getLastName())
                .birthday(personCreateDto.getBirthday())
                .transplantationDate(personCreateDto.getTransplantationDate())
                .organ(personCreateDto.getOrgan())
                .build();

        Mockito.when(personMapper.toEntity(personCreateDto)).thenReturn(person);
        Mockito.when(personRepository.save(person)).thenReturn(person);

        PersonReadDto expectedPersonReadDto = PersonReadDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthday(person.getBirthday())
                .transplantationDate(person.getTransplantationDate())
                .organ(person.getOrgan())
                .build();

        Mockito.when(personMapper.toDto(person)).thenReturn(expectedPersonReadDto);

        PersonReadDto result = personService.create(personCreateDto);

        assertNotNull(result);
        assertEquals(expectedPersonReadDto, result);

        Mockito.verify(personMapper, times(1)).toEntity(personCreateDto);
        Mockito.verify(personRepository, times(1)).save(person);
        Mockito.verify(personMapper, times(1)).toDto(person);
    }

    @Test
    public void getAll_ShouldReturnNonDeletedPersons() {
        List<Person> mockPersons = Arrays.asList(
                Person.builder()
                        .firstName("Maksim")
                        .lastName("Balashov")
                        .birthday(LocalDate.of(1990, 1, 1))
                        .transplantationDate(LocalDate.of(2021, 1, 1))
                        .organ(Arrays.asList(OrganEnum.LUNGS, OrganEnum.KIDNEY))
                        .isDeleted(false)
                        .build(),
                Person.builder()
                        .firstName("Yarik")
                        .lastName("Sapognikov")
                        .birthday(LocalDate.of(1990, 1, 1))
                        .transplantationDate(LocalDate.of(2021, 1, 1))
                        .organ(Collections.singletonList(OrganEnum.KIDNEY))
                        .isDeleted(true)
                        .build()
        );

        Mockito.when(personRepository.findAll()).thenReturn(mockPersons);
        Mockito.when(personMapper.toDto(any(Person.class))).thenAnswer(invocation ->
        {
            Person person = invocation.getArgument(0);
            return new PersonReadDto(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getBirthday(),
                    person.getTransplantationDate(),
                    person.getOrgan()
            );
        });

        List<PersonReadDto> result = personService.getAll();

        assertEquals(1, result.size());
        Assertions.assertAll(
                () -> assertEquals("Maksim", result.get(0).getFirstName()),
                () -> assertEquals(LocalDate.of(1990, 1, 1), result.get(0).getBirthday()),
                () -> assertTrue(result.get(0).getOrgan().containsAll(Arrays.asList(OrganEnum.LUNGS, OrganEnum.KIDNEY)))
        );

        Mockito.verify(personRepository, Mockito.times(1)).findAll();
        Mockito.verify(personMapper, Mockito.times(1)).toDto(any(Person.class));
    }
    @Test
    public void getTodayBirthdays_ShouldReturnTodayBirthdays() {
        LocalDate today = LocalDate.now();
        List<Person> mockPeople = Arrays.asList(
                Person.builder()
                        .firstName("Maksim")
                        .lastName("Balashov")
                        .birthday(today)
                        .isDeleted(false)
                        .build(),
                Person.builder()
                        .firstName("Danil")
                        .lastName("Mamontov")
                        .birthday(today.minusDays(1))
                        .isDeleted(false)
                        .build(),
                Person.builder()
                        .firstName("Yarik")
                        .lastName("Sapognikov")
                        .birthday(today)
                        .isDeleted(true)
                        .build()
        );

        Mockito.when(personRepository.findAll()).thenReturn(mockPeople);
        Mockito.when(personMapper.toDto(any(Person.class))).thenAnswer(invocation -> {
            Person person = invocation.getArgument(0);
            return PersonReadDto.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .birthday(person.getBirthday())
                    .build();
        });

        List<PersonReadDto> result = personService.getTodayBirthdays();

        List<PersonReadDto> expected = mockPeople.stream()
                .filter(person -> !person.getIsDeleted())
                .filter(person -> person.getBirthday().equals(today))
                .map(personMapper::toDto)
                .collect(Collectors.toList());

        assertEquals(expected.size(), result.size());

        Mockito.verify(personRepository, Mockito.times(1)).findAll();
        Mockito.verify(personMapper, Mockito.times(2)).toDto(any(Person.class));
    }
    @Test
    public void getById_ShouldReturnPersonById(){
        UUID id = UUID.randomUUID();
        Person person = Person.builder()
                .id(id)
                .firstName("Maksim")
                .lastName("Balashov")
                .birthday(LocalDate.of(1990, 1, 1))
                .transplantationDate(LocalDate.of(2020, 1, 1))
                .organ(List.of(OrganEnum.HEART))
                .isActivated(true)
                .isDeleted(false)
                .build();

        PersonReadDto personReadDto = PersonReadDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthday(person.getBirthday())
                .transplantationDate(person.getTransplantationDate())
                .organ(person.getOrgan())
                .build();

        Mockito.when(personRepository.findById(id)).thenReturn(java.util.Optional.of(person));
        Mockito.when(personMapper.toDto(any(Person.class))).thenReturn(personReadDto);

        PersonReadDto result = personService.getById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Maksim", result.getFirstName());
        Assertions.assertEquals("Balashov", result.getLastName());
        Assertions.assertEquals(LocalDate.of(1990, 1, 1), result.getBirthday());
        Assertions.assertEquals(LocalDate.of(2020, 1, 1), result.getTransplantationDate());
        Assertions.assertTrue(result.getOrgan().contains(OrganEnum.HEART));

        Mockito.verify(personRepository, times(1)).findById(id);
        Mockito.verify(personMapper, times(1)).toDto(any(Person.class));
    }
    @Test
    void updateTest_ShouldReturnPUpdatePerson() {
        UUID id = UUID.randomUUID();
        PersonUpdateDto personUpdateDto = PersonUpdateDto.builder()
                .firstName("Evgen")
                .lastName("Evgeniev")
                .birthday(LocalDate.of(1991, 1, 1))
                .transplantationDate(LocalDate.of(2021, 1, 1))
                .organ(List.of(OrganEnum.HEART))
                .build();

        Person personBeforeUpdate = Person.builder()
                .firstName("Maksim")
                .lastName("Balashov")
                .birthday(LocalDate.of(1990, 12, 31))
                .transplantationDate(LocalDate.of(2020, 12, 31))
                .organ(List.of(OrganEnum.KIDNEY))
                .isActivated(true)
                .isDeleted(false)
                .build();

        Person personAfterUpdate = Person.builder()
                .firstName(personUpdateDto.getFirstName())
                .lastName(personUpdateDto.getLastName())
                .birthday(personUpdateDto.getBirthday())
                .transplantationDate(personUpdateDto.getTransplantationDate())
                .organ(personUpdateDto.getOrgan())
                .isActivated(true)
                .isDeleted(false)
                .build();

        PersonReadDto expectedDto = PersonReadDto.builder()
                .firstName("Evgen")
                .lastName("Evgeniev")
                .birthday(LocalDate.of(1991, 1, 1))
                .transplantationDate(LocalDate.of(2021, 1, 1))
                .organ(List.of(OrganEnum.HEART))
                .build();

        when(personRepository.findById(id)).thenReturn(java.util.Optional.of(personBeforeUpdate));
        when(personRepository.save(any(Person.class))).thenReturn(personAfterUpdate);
        when(personMapper.toDto(any(Person.class))).thenReturn(expectedDto);

        PersonReadDto resultDto = personService.update(id, personUpdateDto);

        assertNotNull(resultDto);
        assertEquals("Evgen", resultDto.getFirstName());
        assertEquals("Evgeniev", resultDto.getLastName());
        verify(personRepository).findById(id);
        verify(personRepository).save(any(Person.class));
        verify(personMapper).updateEntity(eq(personUpdateDto), any(Person.class));
        verify(personMapper).toDto(any(Person.class));
    }
}
