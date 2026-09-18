package com.pessoal.study_hub.models.dtos;

import com.pessoal.study_hub.models.entities.Topic;

public record TopicResponse(Long id, String name, ColorResponse color, String icon, long fileCount) {

    public static TopicResponse from(Topic topic, long fileCount) {
        return new TopicResponse(
                topic.getId(),
                topic.getName(),
                ColorResponse.from(topic.getColor()),
                topic.getIcon(),
                fileCount);
    }
}
