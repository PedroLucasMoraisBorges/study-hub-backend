package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.Pattern;

/** Atualização parcial: só os campos não nulos são aplicados. Para limpar o texto, enviar "". */
public record NoteUpdateRequest(
        String text,
        @Pattern(regexp = "butter|sage|peach|lavender|mist") String color) {}
