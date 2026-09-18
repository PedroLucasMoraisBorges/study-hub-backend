package com.pessoal.study_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.study_hub.models.entities.DocumentBlock;

public interface DocumentBlockRepository extends JpaRepository<DocumentBlock, Long> {

    List<DocumentBlock> findByFileIdOrderByPositionAsc(Long fileId);

    long countByFileId(Long fileId);
}
