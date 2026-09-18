package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TopicRequest(
        @NotBlank @Size(max = 100) String name,
        @NotNull Long colorId,
        @NotBlank @Pattern(regexp = "code|book|star|flag|target|bulb") String icon) {
}
