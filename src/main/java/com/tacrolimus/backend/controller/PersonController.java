package com.tacrolimus.backend.controller;

import com.tacrolimus.backend.dto.CreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.dto.UpdateDto;
import com.tacrolimus.backend.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/create")
    public PersonReadDto create(@RequestBody CreateDto createDto){
        return personService.create(createDto);
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
    public PersonReadDto update(@PathVariable UUID id, @RequestBody UpdateDto updateDto){
        return personService.update(id,updateDto);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        personService.deleteById(id);
    }
}
