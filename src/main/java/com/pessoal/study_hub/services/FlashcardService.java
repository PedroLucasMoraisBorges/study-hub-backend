package com.pessoal.study_hub.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoal.study_hub.exceptions.ResourceNotFoundException;
import com.pessoal.study_hub.models.dtos.FlashcardRequest;
import com.pessoal.study_hub.models.dtos.FlashcardResponse;
import com.pessoal.study_hub.models.entities.FileEntity;
import com.pessoal.study_hub.models.entities.Flashcard;
import com.pessoal.study_hub.repositories.FlashcardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private static final String TYPE = "cards";

    private final FlashcardRepository flashcardRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<FlashcardResponse> list(Long fileId) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        return flashcardRepository.findByFileIdOrderByPositionAsc(fileId).stream()
                .map(FlashcardResponse::from).toList();
    }

    @Transactional
    public FlashcardResponse create(Long fileId, FlashcardRequest request) {
        FileEntity file = fileService.findFile(fileId);
        fileService.assertType(file, TYPE);
        int position = (int) flashcardRepository.countByFileId(fileId);
        Flashcard card = Flashcard.builder()
                .file(file)
                .position(position)
                .front(request.front())
                .back(request.back())
                .build();
        card = flashcardRepository.save(card);
        fileService.refreshMetadata(fileId);
        return FlashcardResponse.from(card);
    }

    @Transactional
    public FlashcardResponse update(Long fileId, Long cardId, FlashcardRequest request) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        Flashcard card = findCard(fileId, cardId);
        card.setFront(request.front());
        card.setBack(request.back());
        return FlashcardResponse.from(card);
    }

    @Transactional
    public void delete(Long fileId, Long cardId) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        List<Flashcard> cards = flashcardRepository.findByFileIdOrderByPositionAsc(fileId);
        cards.removeIf(c -> c.getId().equals(cardId));
        flashcardRepository.deleteById(cardId);
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setPosition(i);
        }
        flashcardRepository.saveAll(cards);
        fileService.refreshMetadata(fileId);
    }

    private Flashcard findCard(Long fileId, Long cardId) {
        return flashcardRepository.findByFileIdOrderByPositionAsc(fileId).stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Carta não encontrada: " + cardId));
    }
}
