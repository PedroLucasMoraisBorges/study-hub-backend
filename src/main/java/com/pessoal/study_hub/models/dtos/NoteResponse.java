package com.pessoal.study_hub.models.dtos;

import java.time.Instant;

import com.pessoal.study_hub.models.entities.Note;

public record NoteResponse(
        Long id,
        Long documentId,
        Long topicId,
        String text,
        String color,
        Instant createdAt) {

    public static NoteResponse from(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getFile() == null ? null : note.getFile().getId(),
                note.getTopic().getId(),
                note.getText(),
                note.getColor(),
                note.getCreatedAt());
    }
}
