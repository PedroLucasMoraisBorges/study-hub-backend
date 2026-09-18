package com.pessoal.study_hub.models.dtos;

import com.pessoal.study_hub.models.entities.SlideColumn;

public record SlideColumnResponse(Long id, Integer order, String text, String align, String image) {

    public static SlideColumnResponse from(SlideColumn column) {
        return new SlideColumnResponse(
                column.getId(),
                column.getPosition(),
                column.getText(),
                column.getAlign(),
                column.getImage());
    }
}
