package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(description = "DTO, представляющий пару 'Санта - Получатель'")
public class SantaPairReadDto {

    private PersonReadDto santa;

    private PersonReadDto recipient;

    private FileInfoReadDto fileId;

    @Schema(description = "Статус посылки")
    private SantaPairStatusEnum status;

    @Schema(description = "Комментарий")
    private String comment;
}
