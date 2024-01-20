package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.*;


import java.util.UUID;

public interface SantaService {
    RegistrationReadDto register(RegistrationCreateDto registrationCreateDto);
    RegistrationReadDto getRegistrationById(UUID id);
    RegistrationReadDto updateRegistration(UUID id, RegistrationUpdateDto registrationUpdateDto);
    void deleteRegistrationById(UUID id);
    void draw();
    SantaPairReadDto getRecipientBySantaId(UUID santaId);
    StatusReadDto getStatus(UUID id);
    void setStatusTransit(UUID id);
    void setStatusReached(UUID id, StatusReachedDto statusReachedDto);
    void setStatusReceived(UUID id);
}
