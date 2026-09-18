package com.pessoal.study_hub.models.dtos;

import java.time.Instant;
import java.util.Map;

import com.pessoal.study_hub.models.entities.FileEntity;

public record FileSummaryResponse(
        Long id,
        Long topicId,
        String name,
        String description,
        String type,
        Map<String, Object> metadata,
        Instant createdAt,
        Instant updatedAt) {

    public static FileSummaryResponse from(FileEntity file) {
        return new FileSummaryResponse(
                file.getId(),
                file.getTopic().getId(),
                file.getName(),
                file.getDescription(),
                file.getType(),
                file.getMetadata(),
                file.getCreatedAt(),
                file.getUpdatedAt());
    }
}
