package com.pessoal.study_hub.models.dtos;

import java.util.List;

public record FileDetailResponse(
        FileSummaryResponse file,
        List<DocumentBlockResponse> blocks,
        List<FlashcardResponse> cards,
        List<SlideResponse> slides,
        List<MindMapNodeResponse> nodes) {
}
