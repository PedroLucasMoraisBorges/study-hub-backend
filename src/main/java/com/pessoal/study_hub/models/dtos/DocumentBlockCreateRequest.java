package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DocumentBlockCreateRequest(
        @NotBlank @Pattern(regexp = "h1|h2|h3|paragraph|bullet|link|image|file|hr|code|quote") String type,
        Long afterId) {
}
