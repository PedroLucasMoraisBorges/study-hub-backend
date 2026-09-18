package com.pessoal.study_hub.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.study_hub.models.dtos.FlashcardRequest;
import com.pessoal.study_hub.models.dtos.FlashcardResponse;
import com.pessoal.study_hub.services.FlashcardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files/{fileId}/cards")
@RequiredArgsConstructor
public class FlashcardController {

    private final FlashcardService flashcardService;

    @GetMapping
    public List<FlashcardResponse> list(@PathVariable Long fileId) {
        return flashcardService.list(fileId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlashcardResponse create(@PathVariable Long fileId, @Valid @RequestBody FlashcardRequest request) {
        return flashcardService.create(fileId, request);
    }

    @PutMapping("/{cardId}")
    public FlashcardResponse update(@PathVariable Long fileId, @PathVariable Long cardId,
            @Valid @RequestBody FlashcardRequest request) {
        return flashcardService.update(fileId, cardId, request);
    }

    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long fileId, @PathVariable Long cardId) {
        flashcardService.delete(fileId, cardId);
    }
}
