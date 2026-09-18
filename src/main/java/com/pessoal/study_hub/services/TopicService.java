package com.pessoal.study_hub.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoal.study_hub.exceptions.ResourceNotFoundException;
import com.pessoal.study_hub.models.dtos.TopicRequest;
import com.pessoal.study_hub.models.dtos.TopicResponse;
import com.pessoal.study_hub.models.entities.Color;
import com.pessoal.study_hub.models.entities.Topic;
import com.pessoal.study_hub.repositories.ColorRepository;
import com.pessoal.study_hub.repositories.FileRepository;
import com.pessoal.study_hub.repositories.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final ColorRepository colorRepository;
    private final FileRepository fileRepository;

    @Transactional(readOnly = true)
    public List<TopicResponse> listAll() {
        return topicRepository.findAllByOrderByNameAsc().stream()
                .map(topic -> TopicResponse.from(topic, fileRepository.countByTopicId(topic.getId())))
                .toList();
    }

    @Transactional
    public TopicResponse create(TopicRequest request) {
        Color color = findColor(request.colorId());
        Topic topic = Topic.builder()
                .name(request.name())
                .color(color)
                .icon(request.icon())
                .build();
        topic = topicRepository.save(topic);
        return TopicResponse.from(topic, 0);
    }

    @Transactional
    public TopicResponse update(Long topicId, TopicRequest request) {
        Topic topic = findTopic(topicId);
        topic.setName(request.name());
        topic.setColor(findColor(request.colorId()));
        topic.setIcon(request.icon());
        return TopicResponse.from(topic, fileRepository.countByTopicId(topicId));
    }

    @Transactional
    public void delete(Long topicId) {
        if (!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException("Tópico não encontrado: " + topicId);
        }
        topicRepository.deleteById(topicId);
    }

    private Topic findTopic(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico não encontrado: " + id));
    }

    private Color findColor(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cor não encontrada: " + id));
    }
}
