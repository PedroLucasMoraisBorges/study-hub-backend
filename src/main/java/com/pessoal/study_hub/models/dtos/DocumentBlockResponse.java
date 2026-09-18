package com.pessoal.study_hub.models.dtos;

import com.pessoal.study_hub.models.entities.DocumentBlock;

public record DocumentBlockResponse(
        Long id,
        Integer order,
        String type,
        String contentText,
        String linkUrl,
        String imageFile,
        String documentFile,
        String documentFileName,
        String language) {

    public static DocumentBlockResponse from(DocumentBlock block) {
        return new DocumentBlockResponse(
                block.getId(),
                block.getPosition(),
                block.getType(),
                block.getContentText(),
                block.getLinkUrl(),
                block.getImageFile(),
                block.getDocumentFile(),
                block.getDocumentFileName(),
                block.getLanguage());
    }
}
