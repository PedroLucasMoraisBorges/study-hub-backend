package com.pessoal.study_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.study_hub.models.entities.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByOrderByNameAsc();
}
