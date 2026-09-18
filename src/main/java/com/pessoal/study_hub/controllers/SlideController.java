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

import com.pessoal.study_hub.models.dtos.SlideRequest;
import com.pessoal.study_hub.models.dtos.SlideResponse;
import com.pessoal.study_hub.services.SlideService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files/{fileId}/slides")
@RequiredArgsConstructor
public class SlideController {

    private final SlideService slideService;

    @GetMapping
    public List<SlideResponse> list(@PathVariable Long fileId) {
        return slideService.list(fileId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SlideResponse create(@PathVariable Long fileId, @Valid @RequestBody SlideRequest request) {
        return slideService.create(fileId, request);
    }

    @PutMapping("/{slideId}")
    public SlideResponse update(@PathVariable Long fileId, @PathVariable Long slideId,
            @Valid @RequestBody SlideRequest request) {
        return slideService.update(fileId, slideId, request);
    }

    @DeleteMapping("/{slideId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long fileId, @PathVariable Long slideId) {
        slideService.delete(fileId, slideId);
    }
}
