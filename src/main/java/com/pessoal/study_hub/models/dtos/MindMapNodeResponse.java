package com.pessoal.study_hub.models.dtos;

import com.pessoal.study_hub.models.entities.MindMapNode;

public record MindMapNodeResponse(
        Long id,
        Long parentId,
        String label,
        String description,
        Double x,
        Double y,
        Double w,
        Double h,
        String shape,
        ColorResponse color,
        String borderStyle,
        ColorResponse borderColor,
        String fontSize) {

    public static MindMapNodeResponse from(MindMapNode node) {
        return new MindMapNodeResponse(
                node.getId(),
                node.getParent() != null ? node.getParent().getId() : null,
                node.getLabel(),
                node.getDescription(),
                node.getPosX(),
                node.getPosY(),
                node.getWidth(),
                node.getHeight(),
                node.getShape(),
                ColorResponse.from(node.getColor()),
                node.getBorderStyle(),
                ColorResponse.from(node.getBorderColor()),
                node.getFontSize());
    }
}
