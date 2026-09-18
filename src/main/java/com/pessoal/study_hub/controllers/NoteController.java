package com.pessoal.study_hub.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.study_hub.models.dtos.NoteCreateRequest;
import com.pessoal.study_hub.models.dtos.NoteResponse;
import com.pessoal.study_hub.models.dtos.NoteUpdateRequest;
import com.pessoal.study_hub.services.NoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/api/topics/{topicId}/notes")
    public List<NoteResponse> listByTopic(@PathVariable Long topicId) {
        return noteService.listByTopic(topicId);
    }

    @GetMapping("/api/files/{fileId}/notes")
    public List<NoteResponse> listByFile(@PathVariable Long fileId) {
        return noteService.listByFile(fileId);
    }

    @PostMapping("/api/topics/{topicId}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse create(@PathVariable Long topicId,
            @RequestBody(required = false) NoteCreateRequest request) {
        return noteService.create(topicId, request);
    }

    @PutMapping("/api/notes/{noteId}")
    public NoteResponse update(@PathVariable Long noteId, @Valid @RequestBody NoteUpdateRequest request) {
        return noteService.update(noteId, request);
    }

    @DeleteMapping("/api/notes/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long noteId) {
        noteService.delete(noteId);
    }
}
