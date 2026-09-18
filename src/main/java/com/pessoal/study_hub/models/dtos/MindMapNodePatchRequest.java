package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.Pattern;

public record MindMapNodePatchRequest(
        String label,
        String description,
        Double x,
        Double y,
        Double w,
        Double h,
        @Pattern(regexp = "rectangle|circle|square|diamond") String shape,
        Long colorId,
        @Pattern(regexp = "none|solid|dashed|dotted") String borderStyle,
        Long borderColorId,
        @Pattern(regexp = "small|medium|large") String fontSize) {
}
