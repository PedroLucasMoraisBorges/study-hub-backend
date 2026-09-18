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

import com.pessoal.study_hub.models.dtos.DocumentBlockCreateRequest;
import com.pessoal.study_hub.models.dtos.DocumentBlockResponse;
import com.pessoal.study_hub.models.dtos.DocumentBlockUpdateRequest;
import com.pessoal.study_hub.services.DocumentBlockService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files/{fileId}/blocks")
@RequiredArgsConstructor
public class DocumentBlockController {

    private final DocumentBlockService documentBlockService;

    @GetMapping
    public List<DocumentBlockResponse> list(@PathVariable Long fileId) {
        return documentBlockService.list(fileId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentBlockResponse create(@PathVariable Long fileId,
            @Valid @RequestBody DocumentBlockCreateRequest request) {
        return documentBlockService.create(fileId, request);
    }

    @PutMapping("/{blockId}")
    public DocumentBlockResponse update(@PathVariable Long fileId, @PathVariable Long blockId,
            @Valid @RequestBody DocumentBlockUpdateRequest request) {
        return documentBlockService.update(fileId, blockId, request);
    }

    @PostMapping("/{blockId}/move-up")
    public void moveUp(@PathVariable Long fileId, @PathVariable Long blockId) {
        documentBlockService.move(fileId, blockId, -1);
    }

    @PostMapping("/{blockId}/move-down")
    public void moveDown(@PathVariable Long fileId, @PathVariable Long blockId) {
        documentBlockService.move(fileId, blockId, 1);
    }

    @DeleteMapping("/{blockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long fileId, @PathVariable Long blockId) {
        documentBlockService.delete(fileId, blockId);
    }
}
