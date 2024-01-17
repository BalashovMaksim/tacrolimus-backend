package com.tacrolimus.backend.repository;

import com.tacrolimus.backend.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileInfo, UUID> {
}
