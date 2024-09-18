package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.dto.TopicDto;
import com.openclassrooms.mddapi.model.entity.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return the topics.
     */
    public TopicDto[] getTopics() {
        List<Topic> topics = topicRepository.findAllByOrderByTitleAsc();
        return topics.stream().map(topic -> modelMapper.map(topic, TopicDto.class)).toList().toArray(TopicDto[]::new);
    }
}