package com.tacrolimus.backend.controller;

import com.tacrolimus.backend.dto.*;
import com.tacrolimus.backend.service.SantaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/santa")
@RequiredArgsConstructor
public class SantaController {
    private final SantaService santaService;

    @PostMapping("/register")
    public RegistrationReadDto register(@RequestBody RegistrationCreateDto registrationCreateDto){
        return santaService.register(registrationCreateDto);
    }
    @GetMapping("/{id}")
    public RegistrationReadDto getRegistrationById(@PathVariable UUID id){
        return santaService.getRegistrationById(id);
    }
    @PutMapping("/{id}")
    public RegistrationReadDto updateRegistration(@PathVariable UUID id, @RequestBody RegistrationUpdateDto registrationUpdateDto){
        return santaService.updateRegistration(id, registrationUpdateDto);
    }
    @DeleteMapping("/{id}")
    public void deleteRegistrationById(@PathVariable UUID id){
        santaService.deleteRegistrationById(id);
    }
    @PostMapping("/draw")
    public void draw(){
        santaService.draw();
    }
    @GetMapping("/get-recipient/{santaId}")
    public SantaPairReadDto getRecipientBySantaId(@PathVariable UUID santaId){
        return santaService.getRecipientBySantaId(santaId);
    }
    @GetMapping("/status/{id}")
    public StatusReadDto getStatus(@PathVariable UUID id) {
        return santaService.getStatus(id);
    }
    @PatchMapping("/status/set-transit/{id}")
    public void setStatusTransit(@PathVariable UUID id) {
        santaService.setStatusTransit(id);
    }
    @PatchMapping("/status/set-reached/{id}")
    public void setStatusReached(@PathVariable UUID id, @RequestBody StatusReachedDto statusReachedDto) {
        santaService.setStatusReached(id, statusReachedDto);
    }
    @PatchMapping("/status/set-received/{id}")
    public void setStatusReceived(@PathVariable UUID id) {
        santaService.setStatusReceived(id);
    }
}
