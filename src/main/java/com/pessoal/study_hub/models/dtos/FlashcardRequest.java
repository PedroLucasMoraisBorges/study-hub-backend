package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record FlashcardRequest(
        @NotBlank String front,
        @NotBlank String back) {
}
