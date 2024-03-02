package com.tacrolimus.backend.controller;

import com.tacrolimus.backend.dto.*;
import com.tacrolimus.backend.service.SantaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/santa")
@RequiredArgsConstructor
@Tag(name = "Santa Controller")
public class SantaController {
    private final SantaService santaService;

    @PostMapping("/register")
    @Operation(summary = "Регистрация в игре 'Тайный Санта'",
        description = "Позволяет зарегистрировать нового участника в игре",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Участник успешно зарегистрирован",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationReadDto.class))),
            @ApiResponse(responseCode = "400",
                description = "Некорректные данные для регистрации")
    })
    public RegistrationReadDto register(@Valid @RequestBody RegistrationCreateDto registrationCreateDto){
        return santaService.register(registrationCreateDto);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Поиск участника по ID",
        description = "Позволяет найти участника игры 'Тайный Санта' по его идентификатору",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Участник успешно найден",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationReadDto.class))),
            @ApiResponse(responseCode = "404",
                description = "Участник не найден")
    })
    public RegistrationReadDto getRegistrationById(@Parameter(description = "ID участника, которого нужно получить") @PathVariable UUID id){
        return santaService.getRegistrationById(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновление информации об участнике",
        description = "Обновляет данные существующего участника на основе предоставленного ID и тела запроса",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Данные участника успешно обновлены",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationReadDto.class))),
            @ApiResponse(responseCode = "400",
                description = "Неверный запрос, данные не прошли валидацию"),
            @ApiResponse(responseCode = "404",
                description = "Участник с указанным ID не найден")
            }
    )
    public RegistrationReadDto updateRegistration(@Parameter(description = "ID участника для обновления") @PathVariable UUID id, @Valid @RequestBody RegistrationUpdateDto registrationUpdateDto){
        return santaService.updateRegistration(id, registrationUpdateDto);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление участника по ID",
        description = "Удаляет запись о участнике из базы данных по предоставленному идентификатору",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Участник успешно удален"),
            @ApiResponse(responseCode = "404",
                description = "Участник с указанным ID не найден"),
    })
    public void deleteRegistrationById(@Parameter(description = "ID участника для удаления")@PathVariable UUID id){
        santaService.deleteRegistrationById(id);
    }
    @PostMapping("/draw")
    @Operation(summary = "Выбор пар 'Получатель-Санта'",
        description = "Рандомно выбирает пары 'Получатель-Санта' среди зарегистрированных участников",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Пары успешно выбраны"),
            @ApiResponse(responseCode = "400",
                description = "Ошибка в процессе выбора пар")
    })
    public void draw(){
        santaService.draw();
    }
    @GetMapping("/get-recipient/{santaId}")
    @Operation(summary = "Поиск получателя по ID Санты",
        description = "Находит и возвращает данные получателя подарка, назначенного для конкретного Санты",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Получатель найден",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SantaPairReadDto.class))),
            @ApiResponse(responseCode = "404",
                description = "Получатель не найден")
    })
    public SantaPairReadDto getRecipientBySantaId(@Parameter(description = "ID Санты, для которого ищется получатель")  @PathVariable UUID santaId){
        return santaService.getRecipientBySantaId(santaId);
    }
    @GetMapping("/status/{id}")
    @Operation(summary = "Получение информации о паре 'Получатель-Санта'",
        description = "Возвращает актуальную информацию о паре 'Получатель-Санта'",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Информация о паре найдена",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StatusReadDto.class))}),
            @ApiResponse(responseCode = "404",
                description = "Информация не найдена")
    })
    public StatusReadDto getStatus(@Parameter(description = "ID пары для получения информации") @PathVariable UUID id) {
        return santaService.getStatus(id);
    }
    @PatchMapping("/status/set-transit/{id}")
    @Operation(summary = "Установка статуса посылки как 'В пути'",
        description = "Обновляет статус посылки, указывая, что она в процессе доставки",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Статус успешно обновлен"),
            @ApiResponse(responseCode = "404",
                description = "Пара не найдена"),
            @ApiResponse(responseCode = "400",
                description = "Некорректный запрос")
    })
    public void setStatusTransit(@Parameter(description = "ID пары для изменения статуса")@PathVariable UUID id) {
        santaService.setStatusTransit(id);
    }
    @PatchMapping("/status/set-reached/{id}")
    @Operation(summary = "Установка статуса посылки как 'Ожидает получения'",
        description = "Обновляет статус посылки на 'Ожидает получения' с использованием предоставленных данных",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Статус успешно обновлен",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatusReachedDto.class))),
            @ApiResponse(responseCode = "404",
                description = "Пара не найдена"),
            @ApiResponse(responseCode = "400",
                description = "Некорректные данные запроса")
    })
    public void setStatusReached(@Parameter(description = "ID пары для изменения статуса") @PathVariable UUID id,
                                 @Valid @RequestBody StatusReachedDto statusReachedDto) {
        santaService.setStatusReached(id, statusReachedDto);
    }
    @PatchMapping("/status/set-received/{id}")
    @Operation(summary = "Установка статуса посылки как 'Получено'",
        description = "Обновляет статус посылки, указывая, что она в была получена",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Статус успешно обновлен"),
            @ApiResponse(responseCode = "404",
                description = "Пара не найдена"),
            @ApiResponse(responseCode = "400",
                description = "Некорректные данные запроса")
    })
    public void setStatusReceived(@PathVariable UUID id) {
        santaService.setStatusReceived(id);
    }
}
