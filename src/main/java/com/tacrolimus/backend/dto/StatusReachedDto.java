package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class StatusReachedDto {
    private UUID santa;
    private UUID recipient;
    private UUID file;
    private SantaPairStatusEnum newStatus;
    private String comment;
}
