package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.OrganEnum;
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
public abstract class BasePersonSaveDto {
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 20, message = "First name cannot exceed 20 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 25, message = "Last name cannot exceed 25 characters")
    private String lastName;

    @Past(message = "Birthday must be in the past")
    private LocalDate birthday;

    @Past(message = "Transplantation date must be in the past")
    private LocalDate transplantationDate;

    @NotEmpty(message = "At least one organ must be specified")
    private List<OrganEnum> organ;
}
