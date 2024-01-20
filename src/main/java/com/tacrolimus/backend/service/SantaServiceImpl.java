package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.*;
import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import com.tacrolimus.backend.mapper.SantaPairMapper;
import com.tacrolimus.backend.mapper.SantaRegistrationMapper;
import com.tacrolimus.backend.model.FileInfo;
import com.tacrolimus.backend.model.Person;
import com.tacrolimus.backend.model.SantaPair;
import com.tacrolimus.backend.model.SantaRegistration;
import com.tacrolimus.backend.repository.FileInfoRepository;
import com.tacrolimus.backend.repository.PersonRepository;
import com.tacrolimus.backend.repository.SantaPairRepository;
import com.tacrolimus.backend.repository.SantaRegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SantaServiceImpl implements SantaService {
    private final SantaRegistrationRepository santaRegistrationRepository;
    private final SantaPairRepository santaPairRepository;
    private final FileInfoRepository fileInfoRepository;
    private final SantaRegistrationMapper santaRegistrationMapper;
    private final SantaPairMapper santaPairMapper;
    private final PersonRepository personRepository;


    @Override
    @Transactional
    public RegistrationReadDto register(RegistrationCreateDto registrationCreateDto) {
        UUID personId = registrationCreateDto.getPersonId();
        if (personRepository.existsById(personId) && !santaRegistrationRepository.existsByPersonId(personId)) {
            Person person = personRepository.findById(personId)
                    .orElseThrow(() -> new EntityNotFoundException("Person with ID: " + personId + " not found or already deleted"));
            SantaRegistration santaRegistration = santaRegistrationMapper.toEntity(registrationCreateDto);
            santaRegistration.setPerson(person);
            santaRegistration = santaRegistrationRepository.save(santaRegistration);
            return santaRegistrationMapper.toDto(santaRegistration);
        } else {
            throw new IllegalStateException("Person with ID: " + personId + " is already registered");
        }
    }

    @Override
    @Transactional
    public RegistrationReadDto getRegistrationById(UUID id) {
        SantaRegistration santaRegistration = santaRegistrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registration with ID: " + id + " not found or already deleted"));
        return santaRegistrationMapper.toDto(santaRegistration);
    }

    @Override
    @Transactional
    public RegistrationReadDto updateRegistration(UUID id, RegistrationUpdateDto registrationUpdateDto) {
        SantaRegistration santaRegistration = santaRegistrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registration with ID: " + id + " not found or already deleted"));
        if (registrationUpdateDto.getAddress() != null) {
            santaRegistration.setAddress(registrationUpdateDto.getAddress());
        }
        if (registrationUpdateDto.getWishes() != null) {
            santaRegistration.setWishes(registrationUpdateDto.getWishes());
        }
        santaRegistration = santaRegistrationRepository.save(santaRegistration);
        return santaRegistrationMapper.toDto(santaRegistration);
    }

    @Override
    @Transactional
    public void deleteRegistrationById(UUID id) {
        if (!santaRegistrationRepository.existsById(id)) {
            throw new EntityNotFoundException("Registration with ID: " + id + " not found or already deleted");
        }
        santaRegistrationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void draw() {
        if (santaPairRepository.count() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Draw operation is not allowed because pairs already exist.");
        }
        List<SantaRegistration> registrations = santaRegistrationRepository.findAll();
        Collections.shuffle(registrations);

        List<SantaPair> pairs = new ArrayList<>();
        for (int i = 0; i < registrations.size(); i++) {
            SantaRegistration santa = registrations.get(i);
            SantaRegistration recipient = registrations.get((i + 1) % registrations.size());

            SantaPair pair = SantaPair.builder()
                    .santa(santa)
                    .recipient(recipient)
                    .status(SantaPairStatusEnum.CHOOSING)
                    .build();
            pairs.add(pair);
        }
        santaPairRepository.saveAll(pairs);
    }

    @Override
    @Transactional
    public SantaPairReadDto getRecipientBySantaId(UUID santaId) {
        return santaPairRepository.findBySantaId(santaId)
                .map(santaPairMapper::santaPairToSantaPairReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Santa not found or no recipient assigned"));
    }

    @Override
    @Transactional
    public StatusReadDto getStatus(UUID id) {
        SantaPair santaPair = santaPairRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Santa pair not found with id " + id));
        return santaPairMapper.toStatusReadDto(santaPair);
    }

    @Override
    @Transactional
    public void setStatusTransit(UUID id) {
        SantaPair santaPair = santaPairRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Santa pair not found with id " + id));

        santaPair.setStatus(SantaPairStatusEnum.TRANSIT);
        santaPairRepository.save(santaPair);
    }

    @Override
    @Transactional
    public void setStatusReached(UUID id, StatusReachedDto statusReachedDto) {
        SantaPair santaPair = santaPairRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Santa pair not found with id " + id));

        santaPair.setStatus(SantaPairStatusEnum.REACHED);

        if (statusReachedDto.getFile() != null) {
            FileInfo fileInfo = fileInfoRepository.findById(statusReachedDto.getFile())
                    .orElseThrow(() -> new EntityNotFoundException("File not found with id " + statusReachedDto.getFile()));

            santaPair.setFile(fileInfo);
        }

        if (statusReachedDto.getComment() != null) {
            santaPair.setComment(statusReachedDto.getComment());
        }

        santaPairRepository.save(santaPair);
    }
    @Override
    @Transactional
    public void setStatusReceived(UUID id) {
        SantaPair santaPair = santaPairRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("SantaPair not found with id: " + id));
        santaPair.setStatus(SantaPairStatusEnum.RECEIVED);
        santaPairRepository.save(santaPair);
    }
}
