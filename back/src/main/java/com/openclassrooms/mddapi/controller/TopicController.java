package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.dto.TopicDto;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Handles the end points related to the topics.
 */
@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    /***
     * End point that provides all the topics.
     *
     * @return a ResponseEntity containing the topics.
     */
    @GetMapping("/api/topics")
    public ResponseEntity<TopicDto[]> getTopics() {

        return ResponseEntity.ok(topicService.getTopics());
    }

    /**
     * End point that provides a topic.
     *
     * @param id id of the topic.
     * @return a ResponseEntity containing the topic if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @GetMapping("/api/topics/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable("id") final Integer id) {
        Optional<TopicDto> topicDto = topicService.getTopicDtoById(id);
        return topicDto.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * End point that provides the topics the authenticated user is subscribed to.
     *
     * @return a ResponseEntity containing the topics if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @GetMapping("/api/topics/subscribed")
    public ResponseEntity<TopicDto[]> getSubscribedTopics() {
        int id;
        try {
            id = authenticationService.getAuthenticatedUserId();
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(userService.getSubscriptions(id));
    }
}

