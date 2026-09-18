package com.pessoal.study_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.study_hub.models.entities.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    List<Flashcard> findByFileIdOrderByPositionAsc(Long fileId);

    long countByFileId(Long fileId);
}
