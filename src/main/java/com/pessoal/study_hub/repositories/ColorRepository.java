package com.pessoal.study_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.study_hub.models.entities.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
