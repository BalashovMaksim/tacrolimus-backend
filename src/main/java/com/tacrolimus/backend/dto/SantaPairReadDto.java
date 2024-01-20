package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SantaPairReadDto {
    private PersonReadDto santaId;
    private PersonReadDto recipientId;
    private FileInfoReadDto fileId;
    private SantaPairStatusEnum status;
    private String comment;
}
