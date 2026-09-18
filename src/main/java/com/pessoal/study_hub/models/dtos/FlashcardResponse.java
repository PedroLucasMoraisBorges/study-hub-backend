package com.pessoal.study_hub.models.dtos;

import com.pessoal.study_hub.models.entities.Flashcard;

public record FlashcardResponse(Long id, Integer order, String front, String back) {

    public static FlashcardResponse from(Flashcard card) {
        return new FlashcardResponse(card.getId(), card.getPosition(), card.getFront(), card.getBack());
    }
}
