package com.tacrolimus.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@SuperBuilder
@Schema(description = "DTO, представляющее достижение определенного статуса посылки, включая добавление информации о файле и комментарий")
public class StatusReachedDto {

    @NotNull(message = "File cannot be null")
    @Schema(description = "ID файла")
    private UUID file;

    @NotBlank(message = "Comment cannot be blank")
    @Schema(description = "Комментарий")
    private String comment;
}
