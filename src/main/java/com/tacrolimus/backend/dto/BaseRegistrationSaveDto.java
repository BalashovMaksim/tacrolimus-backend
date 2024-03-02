package com.tacrolimus.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Базовый DTO для сохранения информации о участнике")
public class BaseRegistrationSaveDto {
    @NotBlank(message = "Address cannot be blank")
    @Size(max = 100, message = "Address cannot exceed 100 characters")
    @Schema(description = "Адрес")
    private String address;

    @NotBlank(message = "Wishes cannot be blank")
    @Size(max = 100, message = "Wishes cannot exceed 100 characters")
    @Schema(description = "Пожелания")
    private String wishes;
}
