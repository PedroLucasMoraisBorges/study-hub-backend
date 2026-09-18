package com.pessoal.study_hub.models.dtos;

import jakarta.validation.constraints.Size;

public record DocumentBlockUpdateRequest(
        String contentText,
        String linkUrl,
        String imageFile,
        String documentFile,
        String documentFileName,
        @Size(max = 30) String language) {
}
