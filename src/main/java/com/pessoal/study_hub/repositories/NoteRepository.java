package com.pessoal.study_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.study_hub.models.entities.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTopicIdOrderByCreatedAtAscIdAsc(Long topicId);

    List<Note> findByFileIdOrderByCreatedAtAscIdAsc(Long fileId);
}
