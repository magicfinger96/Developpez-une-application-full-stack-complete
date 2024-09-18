package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.dto.TopicDto;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles the end points related to the topics.
 */
@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    /***
     * End point that provides all the topics.
     *
     * @return a ResponseEntity containing the topics.
     */
    @GetMapping("/api/topics")
    public ResponseEntity<TopicDto[]> getTopics() {

        return ResponseEntity.ok(topicService.getTopics());
    }
}

