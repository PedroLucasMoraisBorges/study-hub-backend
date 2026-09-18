package com.pessoal.study_hub.models.dtos;

import java.util.List;

import com.pessoal.study_hub.models.entities.Slide;

public record SlideResponse(
        Long id,
        Integer order,
        String title,
        String layout,
        String titleAlignH,
        String titleAlignV,
        String bgImage,
        List<SlideColumnResponse> columns) {

    public static SlideResponse from(Slide slide) {
        return new SlideResponse(
                slide.getId(),
                slide.getPosition(),
                slide.getTitle(),
                slide.getLayout(),
                slide.getTitleAlignH(),
                slide.getTitleAlignV(),
                slide.getBgImage(),
                slide.getColumns().stream().map(SlideColumnResponse::from).toList());
    }
}
