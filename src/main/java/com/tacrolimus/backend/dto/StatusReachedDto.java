package com.tacrolimus.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@SuperBuilder
public class StatusReachedDto {
    @NotNull(message = "File cannot be null")
    private UUID file;

    @NotBlank(message = "Comment cannot be blank")
    private String comment;
}
