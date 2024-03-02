package com.tacrolimus.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Транспорный DTO, содержащий информацию о участнике")
public class RegistrationReadDto {
    @Schema(description = "Адрес")
    private String address;
    @Schema(description = "Пожелания")
    private String wishes;
    private PersonReadDto person;
}
