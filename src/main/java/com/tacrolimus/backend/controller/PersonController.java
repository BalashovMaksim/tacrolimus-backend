package com.tacrolimus.backend.controller;

import com.tacrolimus.backend.dto.PersonCreateDto;
import com.tacrolimus.backend.dto.PersonReadDto;
import com.tacrolimus.backend.dto.PersonUpdateDto;
import com.tacrolimus.backend.model.Person;
import com.tacrolimus.backend.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
@Tag(name = "Person Controller")
public class PersonController {
    private final PersonService personService;

    @PostMapping("/create")
    @Operation(summary = "Создает нового человека",
        description = "Добавляет нового человека в репозиторий",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Человек успешно создан",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonReadDto.class))),
            @ApiResponse(responseCode = "400",
                description = "Неверные данные для создания человека")
    })
    public PersonReadDto create(@Valid @RequestBody PersonCreateDto personCreateDto){
        return personService.create(personCreateDto);
    }
    @GetMapping("/all")
    @Operation(summary = "Поиск всех людей",
        description = "Находит всех людей в репозитории",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Успешное получение списка людей",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonReadDto.class))),
    })
    public List<PersonReadDto> getAll(){
        return personService.getAll();
    }
    @GetMapping("/today-birthdays")
    @Operation(summary = "Получение списка людей, у которых сегодня день рождения",
        description = "Возвращает список людей, чей день рождения приходится на текущую дату",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Успешно найдены и возвращены люди, празднующие день рождения сегодня",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonReadDto.class))),
            @ApiResponse(responseCode = "404",
                description = "Список пуст"),
    })
    public List<PersonReadDto> getTodayBirthdays(){
        return personService.getTodayBirthdays();
    }
    @GetMapping("/next-birthdays")
    @Operation(summary = "Получение списка людей, у которых день рождения в течение 30 дней",
        description = "Возвращает список людей, чей день рождения приходится на ближайшие 30 дней",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Список людей, празднующих день рождения в ближайшие 30 дней успешно возвращен",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonReadDto.class))),
            @ApiResponse(responseCode = "404",
                description = "Люди, соответствующие условиям, не найдены"),
    })
    public List<PersonReadDto> getNextBirthdays(){
        return personService.getNextBirthdays();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Поиск человека по ID",
        description = "Укажите ID для поиска конкретного человека в репозитории",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Человек успешно найден",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonReadDto.class))),
            @ApiResponse(responseCode = "400",
                description = "Указанный человек не найден")
    })
    public PersonReadDto getById(@Parameter(description = "ID человека, которого нужно получить") @PathVariable UUID id){
        return personService.getById(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных человека",
        description = "Обновляет данные существующего человека на основе предоставленного ID и тела запроса",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Данные человека успешно обновлены",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonReadDto.class))),
            @ApiResponse(responseCode = "400",
                description = "Неверный запрос, данные не прошли валидацию"),
            @ApiResponse(responseCode = "404",
                description = "Человек с указанным ID не найден"),
    })
    public PersonReadDto update(@Parameter(description = "ID человека для обновления") @PathVariable UUID id, @Valid @RequestBody PersonUpdateDto personUpdateDto){
        return personService.update(id,personUpdateDto);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление человека по ID",
        description = "Удаляет запись о человеке из базы данных по предоставленному идентификатору",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Человек успешно удален"),
            @ApiResponse(responseCode = "404",
                description = "Человек с указанным ID не найден"),
    })
    public void deleteById(@Parameter(description = "ID человека для удаления") @PathVariable UUID id){
        personService.deleteById(id);
    }
}
