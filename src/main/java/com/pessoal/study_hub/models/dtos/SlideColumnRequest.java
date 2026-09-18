package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.Pattern;

public record SlideColumnRequest(
        String text,
        @Pattern(regexp = "left|center|right") String align,
        String image) {
}
