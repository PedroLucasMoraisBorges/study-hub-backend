package com.pessoal.study_hub.models.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SlideRequest(
        String title,
        @Pattern(regexp = "single|two|three") String layout,
        @Pattern(regexp = "left|center|right") String titleAlignH,
        @Pattern(regexp = "top|center|bottom") String titleAlignV,
        String bgImage,
        @NotEmpty @Size(max = 3) List<@Valid SlideColumnRequest> columns) {
}
