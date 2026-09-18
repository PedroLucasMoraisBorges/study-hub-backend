package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MindMapNodeCreateRequest(
        Long parentId,
        @NotBlank String label,
        @NotNull Double x,
        @NotNull Double y,
        @NotNull Double w,
        @NotNull Double h) {
}
