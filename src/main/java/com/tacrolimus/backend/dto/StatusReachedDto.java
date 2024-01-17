package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class StatusReachedDto {
    private UUID file;
    private SantaPairStatusEnum newStatus;
    private String comment;
}
