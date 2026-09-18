package com.pessoal.study_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.study_hub.models.entities.MindMapNode;

public interface MindMapNodeRepository extends JpaRepository<MindMapNode, Long> {

    List<MindMapNode> findByFileIdOrderByIdAsc(Long fileId);

    long countByFileId(Long fileId);
}
