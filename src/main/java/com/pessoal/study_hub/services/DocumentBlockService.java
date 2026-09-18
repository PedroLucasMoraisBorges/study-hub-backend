package com.pessoal.study_hub.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoal.study_hub.exceptions.ResourceNotFoundException;
import com.pessoal.study_hub.models.dtos.DocumentBlockCreateRequest;
import com.pessoal.study_hub.models.dtos.DocumentBlockResponse;
import com.pessoal.study_hub.models.dtos.DocumentBlockUpdateRequest;
import com.pessoal.study_hub.models.entities.DocumentBlock;
import com.pessoal.study_hub.models.entities.FileEntity;
import com.pessoal.study_hub.repositories.DocumentBlockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentBlockService {

    private static final String TYPE = "doc";

    private final DocumentBlockRepository documentBlockRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<DocumentBlockResponse> list(Long fileId) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        return documentBlockRepository.findByFileIdOrderByPositionAsc(fileId).stream()
                .map(DocumentBlockResponse::from).toList();
    }

    @Transactional
    public DocumentBlockResponse create(Long fileId, DocumentBlockCreateRequest request) {
        FileEntity file = fileService.findFile(fileId);
        fileService.assertType(file, TYPE);

        List<DocumentBlock> blocks = documentBlockRepository.findByFileIdOrderByPositionAsc(fileId);
        int insertIndex = blocks.size();
        if (request.afterId() != null) {
            for (int i = 0; i < blocks.size(); i++) {
                if (blocks.get(i).getId().equals(request.afterId())) {
                    insertIndex = i + 1;
                    break;
                }
            }
        }

        DocumentBlock newBlock = DocumentBlock.builder()
                .file(file)
                .position(insertIndex)
                .type(request.type())
                .build();

        blocks.add(insertIndex, newBlock);
        resequenceAndSave(blocks);
        fileService.refreshMetadata(fileId);
        return DocumentBlockResponse.from(newBlock);
    }

    @Transactional
    public DocumentBlockResponse update(Long fileId, Long blockId, DocumentBlockUpdateRequest request) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        DocumentBlock block = findBlock(fileId, blockId);
        if (request.contentText() != null) {
            block.setContentText(request.contentText());
        }
        if (request.linkUrl() != null) {
            block.setLinkUrl(request.linkUrl());
        }
        if (request.imageFile() != null) {
            block.setImageFile(request.imageFile());
        }
        if (request.documentFile() != null) {
            block.setDocumentFile(request.documentFile());
        }
        if (request.documentFileName() != null) {
            block.setDocumentFileName(request.documentFileName());
        }
        fileService.refreshMetadata(fileId);
        return DocumentBlockResponse.from(block);
    }

    @Transactional
    public void move(Long fileId, Long blockId, int direction) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        List<DocumentBlock> blocks = documentBlockRepository.findByFileIdOrderByPositionAsc(fileId);
        int index = -1;
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getId().equals(blockId)) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            throw new ResourceNotFoundException("Bloco não encontrado: " + blockId);
        }
        int swapIndex = index + direction;
        if (swapIndex < 0 || swapIndex >= blocks.size()) {
            return;
        }
        DocumentBlock a = blocks.get(index);
        DocumentBlock b = blocks.get(swapIndex);
        int tmp = a.getPosition();
        a.setPosition(b.getPosition());
        b.setPosition(tmp);
    }

    @Transactional
    public void delete(Long fileId, Long blockId) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        List<DocumentBlock> blocks = documentBlockRepository.findByFileIdOrderByPositionAsc(fileId);
        blocks.removeIf(b -> b.getId().equals(blockId));
        documentBlockRepository.deleteById(blockId);
        resequenceAndSave(blocks);
        fileService.refreshMetadata(fileId);
    }

    private void resequenceAndSave(List<DocumentBlock> blocks) {
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).setPosition(i);
        }
        documentBlockRepository.saveAll(blocks);
    }

    private DocumentBlock findBlock(Long fileId, Long blockId) {
        return documentBlockRepository.findByFileIdOrderByPositionAsc(fileId).stream()
                .filter(b -> b.getId().equals(blockId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Bloco não encontrado: " + blockId));
    }
}
