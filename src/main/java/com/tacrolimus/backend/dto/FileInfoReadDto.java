package com.tacrolimus.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Транспортный DTO, содержащий информацию о файле")
public class FileInfoReadDto {

    @Schema(description = "URL файла")
    private String url;

    @Schema(description = "Имя файла")
    private String name;

    @Schema(description = "Размер файла в байтах")
    private int size;
}
