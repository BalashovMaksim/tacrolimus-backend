package com.tacrolimus.backend.repository;

import com.tacrolimus.backend.model.SantaPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SantaPairRepository extends JpaRepository<SantaPair, UUID> {
    Optional<SantaPair> findBySantaId(UUID santaId);

}
