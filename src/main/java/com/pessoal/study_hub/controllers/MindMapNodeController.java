package com.pessoal.study_hub.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.study_hub.models.dtos.MindMapNodeCreateRequest;
import com.pessoal.study_hub.models.dtos.MindMapNodePatchRequest;
import com.pessoal.study_hub.models.dtos.MindMapNodeResponse;
import com.pessoal.study_hub.services.MindMapNodeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files/{fileId}/mindmap/nodes")
@RequiredArgsConstructor
public class MindMapNodeController {

    private final MindMapNodeService mindMapNodeService;

    @GetMapping
    public List<MindMapNodeResponse> list(@PathVariable Long fileId) {
        return mindMapNodeService.list(fileId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MindMapNodeResponse create(@PathVariable Long fileId,
            @Valid @RequestBody MindMapNodeCreateRequest request) {
        return mindMapNodeService.create(fileId, request);
    }

    @PatchMapping("/{nodeId}")
    public MindMapNodeResponse patch(@PathVariable Long fileId, @PathVariable Long nodeId,
            @Valid @RequestBody MindMapNodePatchRequest request) {
        return mindMapNodeService.patch(fileId, nodeId, request);
    }

    @DeleteMapping("/{nodeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long fileId, @PathVariable Long nodeId) {
        mindMapNodeService.delete(fileId, nodeId);
    }
}
