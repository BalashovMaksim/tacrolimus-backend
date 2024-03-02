package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Schema(description = "DTO, представляющее достижение определенного статуса посылки, включая информацию о файле и комментарий")
public class StatusReadDto {

    private FileInfoReadDto file;

    @Schema(description = "Статус посылки")
    private SantaPairStatusEnum currentStatus;

    @Schema(description = "Комментарий")
    private String comment;
}
