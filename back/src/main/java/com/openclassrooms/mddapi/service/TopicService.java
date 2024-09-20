package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.dto.TopicDto;
import com.openclassrooms.mddapi.model.entity.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Service which handles topics logic.
 */
@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all the topics in alphabetical order.
     *
     * @return the topic dtos.
     */
    public TopicDto[] getTopics() {
        Collection<Topic> topics = topicRepository.findAllByOrderByTitleAsc();
        return topics.stream().map(topic -> modelMapper.map(topic, TopicDto.class)).toList().toArray(TopicDto[]::new);
    }

    /**
     * Get a topic DTO.
     *
     * @param id id of the fetched topic.
     * @return a topic dto.
     */
    public Optional<TopicDto> getTopicDtoById(Integer id) {
        Optional<Topic> topic = topicRepository.findById(id);

        if (topic.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(modelMapper.map(topic, TopicDto.class));
    }

    /**
     * Get a topic.
     *
     * @param id id of the fetched topic.
     * @return a topic.
     */
    public Optional<Topic> getTopicById(Integer id) {
        return topicRepository.findById(id);
    }
}