package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.*;
import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import com.tacrolimus.backend.exception.*;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SantaService {
    private final SantaRegistrationRepository santaRegistrationRepository;
    private final SantaPairRepository santaPairRepository;
    private final PersonRepository personRepository;
    private final FileInfoRepository fileInfoRepository;
    private final SantaRegistrationMapper santaRegistrationMapper;
    private final SantaPairMapper santaPairMapper;

    @Transactional
    public RegistrationReadDto register(RegistrationCreateDto registrationCreateDto) {
        UUID personId = registrationCreateDto.getPersonId();
        if (santaRegistrationRepository.existsByPersonId(personId)) {
            throw new PersonAlreadyRegisteredException(personId);
        }
        Person person = findById(personId);

        SantaRegistration santaRegistration = santaRegistrationMapper.toEntity(registrationCreateDto);
        santaRegistration.setPerson(person);
        santaRegistration = santaRegistrationRepository.save(santaRegistration);

        return santaRegistrationMapper.toDto(santaRegistration);
    }

    @Transactional
    public RegistrationReadDto getRegistrationById(UUID id) {
        SantaRegistration santaRegistration = findRegistrationById(id);
        return santaRegistrationMapper.toDto(santaRegistration);
    }

    @Transactional
    public RegistrationReadDto updateRegistration(UUID id, RegistrationUpdateDto registrationUpdateDto) {
        SantaRegistration santaRegistration = findRegistrationById(id);

        santaRegistration.setAddress(registrationUpdateDto.getAddress());
        santaRegistration.setWishes(registrationUpdateDto.getWishes());

        santaRegistration = santaRegistrationRepository.save(santaRegistration);
        return santaRegistrationMapper.toDto(santaRegistration);
    }

    @Transactional
    public void deleteRegistrationById(UUID id) {
        findRegistrationById(id);
        santaRegistrationRepository.deleteById(id);
    }

    @Transactional
    public void draw() {
        if (santaPairRepository.count() > 0) {
            throw new DrawOperationNotAllowedException(HttpStatus.BAD_REQUEST);
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

    @Transactional
    public SantaPairReadDto getRecipientBySantaId(UUID santaId) {
        return santaPairRepository.findBySantaId(santaId)
                .map(santaPairMapper::santaPairToSantaPairReadDto)
                .orElseThrow(() -> new RecipientNotFoundException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public StatusReadDto getStatus(UUID id) {
        SantaPair santaPair = findPairById(id);
        return santaPairMapper.toStatusReadDto(santaPair);
    }

    @Transactional
    public void setStatusTransit(UUID id) {
        SantaPair santaPair = findPairById(id);

        santaPair.setStatus(SantaPairStatusEnum.TRANSIT);
        santaPairRepository.save(santaPair);
    }

    @Transactional
    public void setStatusReached(UUID id, StatusReachedDto statusReachedDto) {
        SantaPair santaPair = findPairById(id);

        santaPair.setStatus(SantaPairStatusEnum.REACHED);

        FileInfo fileInfo = findFileById(statusReachedDto.getFile());

        santaPair.setFile(fileInfo);
        santaPair.setComment(statusReachedDto.getComment());
        santaPairRepository.save(santaPair);
    }
    @Transactional
    public void setStatusReceived(UUID id) {
        SantaPair santaPair = findPairById(id);
        santaPair.setStatus(SantaPairStatusEnum.RECEIVED);
        santaPairRepository.save(santaPair);
    }
    public Person findById(UUID id){
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
    public SantaRegistration findRegistrationById(UUID id){
        return santaRegistrationRepository.findById(id)
                .orElseThrow(() -> new SantaRegistrationNotFoundException(id));
    }
    public SantaPair findPairById(UUID id) {
        return santaPairRepository.findById(id)
                .orElseThrow(() -> new SantaPairNotFoundException(id));
    }
    public FileInfo findFileById(UUID id){
        return fileInfoRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));
    }
}
