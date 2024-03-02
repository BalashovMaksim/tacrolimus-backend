package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.OrganEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Базовый DTO для сохранения информации о человеке")
public abstract class BasePersonSaveDto {

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 20, message = "First name cannot exceed 20 characters")
    @Schema(description = "Имя")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 25, message = "Last name cannot exceed 25 characters")
    @Schema(description = "Фамилия")
    private String lastName;

    @Past(message = "Birthday must be in the past")
    @Schema(description = "Дата рождения", type = "string", format = "date")
    private LocalDate birthday;

    @Past(message = "Transplantation date must be in the past")
    @Schema(description = "Дата трансплантации", type = "string", format = "date")
    private LocalDate transplantationDate;

    @NotEmpty(message = "At least one organ must be specified")
    @Schema(description = "Список трансплантированных органов")
    private List<OrganEnum> organ;
}
