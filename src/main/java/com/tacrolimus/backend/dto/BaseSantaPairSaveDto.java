package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseSantaPairSaveDto {
    private UUID santaId;
    private UUID recipientId;
    private UUID fileId;
    private SantaPairStatusEnum status;
    private String comment;
}
