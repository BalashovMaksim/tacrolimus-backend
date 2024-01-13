package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class StatusReadDto {
    private UUID santa;
    private UUID recipient;
    private SantaPairStatusEnum currentStatus;
}
