package com.pessoal.study_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.study_hub.models.entities.Slide;

public interface SlideRepository extends JpaRepository<Slide, Long> {

    List<Slide> findByFileIdOrderByPositionAsc(Long fileId);

    long countByFileId(Long fileId);
}
