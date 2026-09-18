package com.pessoal.study_hub.models.dtos;

import com.pessoal.study_hub.models.entities.Color;

public record ColorResponse(Long id, String slug, String hexadecimal) {

    public static ColorResponse from(Color color) {
        if (color == null) {
            return null;
        }
        return new ColorResponse(color.getId(), color.getSlug(), color.getHexadecimal());
    }
}
