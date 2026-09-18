package com.pessoal.study_hub.models.dtos;

public record DocumentBlockUpdateRequest(
        String contentText,
        String linkUrl,
        String imageFile,
        String documentFile,
        String documentFileName) {
}
