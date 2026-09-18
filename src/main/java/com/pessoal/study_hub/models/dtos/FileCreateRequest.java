package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FileCreateRequest(
        @NotBlank @Size(max = 200) String name,
        @NotBlank @Pattern(regexp = "doc|cards|mindmap|slides") String type) {
}
