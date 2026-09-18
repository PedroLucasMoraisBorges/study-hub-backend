package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FileUpdateRequest(
        @NotBlank @Size(max = 200) String name,
        @Size(max = 2000) String description) {
}
