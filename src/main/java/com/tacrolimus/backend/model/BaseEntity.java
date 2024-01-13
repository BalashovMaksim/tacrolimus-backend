package com.tacrolimus.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    private UUID id;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
