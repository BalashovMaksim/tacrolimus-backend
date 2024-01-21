package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class StatusReadDto {
    private FileInfoReadDto file;
    private SantaPairStatusEnum  currentStatus;
    private String comment;
}
