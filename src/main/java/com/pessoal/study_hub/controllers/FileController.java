package com.pessoal.study_hub.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.study_hub.models.dtos.FileCreateRequest;
import com.pessoal.study_hub.models.dtos.FileDetailResponse;
import com.pessoal.study_hub.models.dtos.FileSummaryResponse;
import com.pessoal.study_hub.models.dtos.FileUpdateRequest;
import com.pessoal.study_hub.services.FileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/api/topics/{topicId}/files")
    public List<FileSummaryResponse> listByTopic(
            @PathVariable Long topicId,
            @RequestParam(required = false) String type) {
        return fileService.listByTopic(topicId, type);
    }

    @PostMapping("/api/topics/{topicId}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public FileSummaryResponse create(@PathVariable Long topicId, @Valid @RequestBody FileCreateRequest request) {
        return fileService.create(topicId, request);
    }

    @GetMapping("/api/files/{id}")
    public FileDetailResponse getDetail(@PathVariable Long id) {
        return fileService.getDetail(id);
    }

    @PutMapping("/api/files/{id}")
    public FileSummaryResponse update(@PathVariable Long id, @Valid @RequestBody FileUpdateRequest request) {
        return fileService.update(id, request);
    }

    @DeleteMapping("/api/files/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fileService.delete(id);
    }
}
