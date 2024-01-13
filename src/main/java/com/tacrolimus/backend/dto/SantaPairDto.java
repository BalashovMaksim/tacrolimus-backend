package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class SantaPairDto {
    private UUID santaId;
    private UUID recipientId;
    private UUID fileId;
    private SantaPairStatusEnum status;
    private String comment;
}
