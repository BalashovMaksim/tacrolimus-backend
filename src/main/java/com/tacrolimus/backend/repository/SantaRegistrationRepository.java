package com.tacrolimus.backend.repository;

import com.tacrolimus.backend.model.SantaRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SantaRegistrationRepository extends JpaRepository<SantaRegistration, UUID> {
    boolean existsByPersonId(UUID personId);
}
