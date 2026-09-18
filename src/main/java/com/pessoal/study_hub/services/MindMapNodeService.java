package com.pessoal.study_hub.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoal.study_hub.exceptions.ResourceNotFoundException;
import com.pessoal.study_hub.models.dtos.MindMapNodeCreateRequest;
import com.pessoal.study_hub.models.dtos.MindMapNodePatchRequest;
import com.pessoal.study_hub.models.dtos.MindMapNodeResponse;
import com.pessoal.study_hub.models.entities.Color;
import com.pessoal.study_hub.models.entities.FileEntity;
import com.pessoal.study_hub.models.entities.MindMapNode;
import com.pessoal.study_hub.repositories.ColorRepository;
import com.pessoal.study_hub.repositories.MindMapNodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MindMapNodeService {

    private static final String TYPE = "mindmap";

    private final MindMapNodeRepository mindMapNodeRepository;
    private final ColorRepository colorRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<MindMapNodeResponse> list(Long fileId) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        return mindMapNodeRepository.findByFileIdOrderByIdAsc(fileId).stream()
                .map(MindMapNodeResponse::from).toList();
    }

    @Transactional
    public MindMapNodeResponse create(Long fileId, MindMapNodeCreateRequest request) {
        FileEntity file = fileService.findFile(fileId);
        fileService.assertType(file, TYPE);

        MindMapNode parent = null;
        if (request.parentId() != null) {
            parent = mindMapNodeRepository.findById(request.parentId())
                    .filter(n -> n.getFile().getId().equals(fileId))
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Nó pai não encontrado neste arquivo: " + request.parentId()));
        }

        MindMapNode node = MindMapNode.builder()
                .file(file)
                .parent(parent)
                .label(request.label())
                .posX(request.x())
                .posY(request.y())
                .width(request.w())
                .height(request.h())
                .build();

        node = mindMapNodeRepository.save(node);
        fileService.refreshMetadata(fileId);
        return MindMapNodeResponse.from(node);
    }

    @Transactional
    public MindMapNodeResponse patch(Long fileId, Long nodeId, MindMapNodePatchRequest request) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        MindMapNode node = findNode(fileId, nodeId);

        if (request.label() != null) {
            node.setLabel(request.label());
        }
        if (request.description() != null) {
            node.setDescription(request.description());
        }
        if (request.x() != null) {
            node.setPosX(request.x());
        }
        if (request.y() != null) {
            node.setPosY(request.y());
        }
        if (request.w() != null) {
            node.setWidth(request.w());
        }
        if (request.h() != null) {
            node.setHeight(request.h());
        }
        if (request.shape() != null) {
            node.setShape(request.shape());
        }
        if (request.colorId() != null) {
            node.setColor(findColor(request.colorId()));
        }
        if (request.borderStyle() != null) {
            node.setBorderStyle(request.borderStyle());
        }
        if (request.borderColorId() != null) {
            node.setBorderColor(findColor(request.borderColorId()));
        }
        if (request.fontSize() != null) {
            node.setFontSize(request.fontSize());
        }

        return MindMapNodeResponse.from(node);
    }

    @Transactional
    public void delete(Long fileId, Long nodeId) {
        fileService.assertType(fileService.findFile(fileId), TYPE);
        findNode(fileId, nodeId);
        // ON DELETE CASCADE na FK fk_parent remove os descendentes automaticamente.
        mindMapNodeRepository.deleteById(nodeId);
        fileService.refreshMetadata(fileId);
    }

    private MindMapNode findNode(Long fileId, Long nodeId) {
        return mindMapNodeRepository.findById(nodeId)
                .filter(n -> n.getFile().getId().equals(fileId))
                .orElseThrow(() -> new ResourceNotFoundException("Nó não encontrado: " + nodeId));
    }

    private Color findColor(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cor não encontrada: " + id));
    }
}
