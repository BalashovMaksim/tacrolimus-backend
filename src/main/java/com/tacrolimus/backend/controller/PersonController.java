package com.tacrolimus.backend.controller;

import com.tacrolimus.backend.dto.PersonCreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.dto.PersonUpdateDto;
import com.tacrolimus.backend.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping("/create")
    public PersonReadDto create(@RequestBody PersonCreateDto personCreateDto){
        return personService.create(personCreateDto);
    }
    @GetMapping("/all")
    public List<PersonReadDto> getAll(){
        return personService.getAll();
    }
    @GetMapping("/today-birthdays")
    public List<PersonReadDto> getTodayBirthdays(){
        return personService.getTodayBirthdays();
    }
    @GetMapping("/next-birthdays")
    public List<PersonReadDto> getNextBirthdays(){
        return personService.getNextBirthdays();
    }
    @GetMapping("/{id}")
    public PersonReadDto getById(@PathVariable UUID id){
        return personService.getById(id);
    }
    @PutMapping("/{id}")
    public PersonReadDto update(@PathVariable UUID id, @RequestBody PersonUpdateDto personUpdateDto){
        return personService.update(id,personUpdateDto);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        personService.deleteById(id);
    }
}
