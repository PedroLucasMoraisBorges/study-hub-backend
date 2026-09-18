package com.pessoal.study_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.study_hub.models.entities.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findByTopicIdOrderByUpdatedAtDesc(Long topicId);

    List<FileEntity> findByTopicIdAndTypeOrderByUpdatedAtDesc(Long topicId, String type);

    long countByTopicId(Long topicId);
}
