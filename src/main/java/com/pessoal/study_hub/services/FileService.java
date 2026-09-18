package com.pessoal.study_hub.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoal.study_hub.exceptions.InvalidFileTypeException;
import com.pessoal.study_hub.exceptions.ResourceNotFoundException;
import com.pessoal.study_hub.models.dtos.DocumentBlockResponse;
import com.pessoal.study_hub.models.dtos.FileCreateRequest;
import com.pessoal.study_hub.models.dtos.FileDetailResponse;
import com.pessoal.study_hub.models.dtos.FileSummaryResponse;
import com.pessoal.study_hub.models.dtos.FileUpdateRequest;
import com.pessoal.study_hub.models.dtos.FlashcardResponse;
import com.pessoal.study_hub.models.dtos.MindMapNodeResponse;
import com.pessoal.study_hub.models.dtos.SlideResponse;
import com.pessoal.study_hub.models.entities.FileEntity;
import com.pessoal.study_hub.models.entities.Topic;
import com.pessoal.study_hub.repositories.DocumentBlockRepository;
import com.pessoal.study_hub.repositories.FileRepository;
import com.pessoal.study_hub.repositories.FlashcardRepository;
import com.pessoal.study_hub.repositories.MindMapNodeRepository;
import com.pessoal.study_hub.repositories.SlideRepository;
import com.pessoal.study_hub.repositories.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final TopicRepository topicRepository;
    private final DocumentBlockRepository documentBlockRepository;
    private final FlashcardRepository flashcardRepository;
    private final SlideRepository slideRepository;
    private final MindMapNodeRepository mindMapNodeRepository;

    @Transactional(readOnly = true)
    public List<FileSummaryResponse> listByTopic(Long topicId, String type) {
        if (!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException("Tópico não encontrado: " + topicId);
        }
        List<FileEntity> files = (type == null || type.isBlank())
                ? fileRepository.findByTopicIdOrderByUpdatedAtDesc(topicId)
                : fileRepository.findByTopicIdAndTypeOrderByUpdatedAtDesc(topicId, type);
        return files.stream().map(FileSummaryResponse::from).toList();
    }

    @Transactional
    public FileSummaryResponse create(Long topicId, FileCreateRequest request) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico não encontrado: " + topicId));
        FileEntity file = FileEntity.builder()
                .topic(topic)
                .name(request.name())
                .description("")
                .type(request.type())
                .metadata(Map.of("count", 0))
                .build();
        return FileSummaryResponse.from(fileRepository.save(file));
    }

    @Transactional(readOnly = true)
    public FileDetailResponse getDetail(Long fileId) {
        FileEntity file = findFile(fileId);
        List<DocumentBlockResponse> blocks = null;
        List<FlashcardResponse> cards = null;
        List<SlideResponse> slides = null;
        List<MindMapNodeResponse> nodes = null;

        switch (file.getType()) {
            case "doc" -> blocks = documentBlockRepository.findByFileIdOrderByPositionAsc(fileId).stream()
                    .map(DocumentBlockResponse::from).toList();
            case "cards" -> cards = flashcardRepository.findByFileIdOrderByPositionAsc(fileId).stream()
                    .map(FlashcardResponse::from).toList();
            case "slides" -> slides = slideRepository.findByFileIdOrderByPositionAsc(fileId).stream()
                    .map(SlideResponse::from).toList();
            case "mindmap" -> nodes = mindMapNodeRepository.findByFileIdOrderByIdAsc(fileId).stream()
                    .map(MindMapNodeResponse::from).toList();
            default -> throw new IllegalStateException("Tipo de arquivo desconhecido: " + file.getType());
        }

        return new FileDetailResponse(FileSummaryResponse.from(file), blocks, cards, slides, nodes);
    }

    @Transactional
    public FileSummaryResponse update(Long fileId, FileUpdateRequest request) {
        FileEntity file = findFile(fileId);
        file.setName(request.name());
        file.setDescription(request.description() == null ? "" : request.description());
        return FileSummaryResponse.from(file);
    }

    @Transactional
    public void delete(Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResourceNotFoundException("Arquivo não encontrado: " + fileId);
        }
        fileRepository.deleteById(fileId);
    }

    /** Recalcula files.metadata.count após qualquer mutação nos filhos e toca updated_at. */
    @Transactional
    public void refreshMetadata(Long fileId) {
        FileEntity file = findFile(fileId);
        long count = switch (file.getType()) {
            case "doc" -> documentBlockRepository.countByFileId(fileId);
            case "cards" -> flashcardRepository.countByFileId(fileId);
            case "slides" -> slideRepository.countByFileId(fileId);
            case "mindmap" -> mindMapNodeRepository.countByFileId(fileId);
            default -> 0;
        };
        file.setMetadata(Map.of("count", count));
    }

    FileEntity findFile(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Arquivo não encontrado: " + fileId));
    }

    void assertType(FileEntity file, String expectedType) {
        if (!file.getType().equals(expectedType)) {
            throw new InvalidFileTypeException(
                    "Arquivo " + file.getId() + " é do tipo '" + file.getType() + "', esperado '" + expectedType
                            + "'");
        }
    }
}
