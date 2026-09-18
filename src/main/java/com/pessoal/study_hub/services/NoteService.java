package com.pessoal.study_hub.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoal.study_hub.exceptions.ResourceNotFoundException;
import com.pessoal.study_hub.models.dtos.NoteCreateRequest;
import com.pessoal.study_hub.models.dtos.NoteResponse;
import com.pessoal.study_hub.models.dtos.NoteUpdateRequest;
import com.pessoal.study_hub.models.entities.FileEntity;
import com.pessoal.study_hub.models.entities.Note;
import com.pessoal.study_hub.models.entities.Topic;
import com.pessoal.study_hub.repositories.NoteRepository;
import com.pessoal.study_hub.repositories.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService {

    private static final String DOC_TYPE = "doc";

    private final NoteRepository noteRepository;
    private final TopicRepository topicRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<NoteResponse> listByTopic(Long topicId) {
        if (!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException("Tópico não encontrado: " + topicId);
        }
        return noteRepository.findByTopicIdOrderByCreatedAtAscIdAsc(topicId).stream()
                .map(NoteResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> listByFile(Long fileId) {
        fileService.assertType(fileService.findFile(fileId), DOC_TYPE);
        return noteRepository.findByFileIdOrderByCreatedAtAscIdAsc(fileId).stream()
                .map(NoteResponse::from).toList();
    }

    @Transactional
    public NoteResponse create(Long topicId, NoteCreateRequest request) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico não encontrado: " + topicId));
        Note note = Note.builder().topic(topic).file(resolveFile(topicId, request)).build();
        return NoteResponse.from(noteRepository.save(note));
    }

    @Transactional
    public NoteResponse update(Long noteId, NoteUpdateRequest request) {
        Note note = findNote(noteId);
        if (request.text() != null) {
            note.setText(request.text());
        }
        if (request.color() != null) {
            note.setColor(request.color());
        }
        return NoteResponse.from(note);
    }

    @Transactional
    public void delete(Long noteId) {
        if (!noteRepository.existsById(noteId)) {
            throw new ResourceNotFoundException("Nota não encontrada: " + noteId);
        }
        noteRepository.deleteById(noteId);
    }

    /** Nota avulsa (sem documentId) devolve null; senão o documento precisa ser do tipo doc e do mesmo tópico. */
    private FileEntity resolveFile(Long topicId, NoteCreateRequest request) {
        if (request == null || request.documentId() == null) {
            return null;
        }
        FileEntity file = fileService.findFile(request.documentId());
        fileService.assertType(file, DOC_TYPE);
        if (!file.getTopic().getId().equals(topicId)) {
            throw new ResourceNotFoundException(
                    "Documento não encontrado neste tópico: " + request.documentId());
        }
        return file;
    }

    private Note findNote(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Nota não encontrada: " + noteId));
    }
}
