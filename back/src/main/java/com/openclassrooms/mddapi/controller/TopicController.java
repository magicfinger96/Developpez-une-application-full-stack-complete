package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.dto.TopicDto;
import com.openclassrooms.mddapi.model.response.MessageResponse;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * End point that subscribe the authenticated user to a topic.
     *
     * @param id id of the topic.
     * @return a ResponseEntity containing a MessageResponse if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @PostMapping("/api/topics/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable("id") final Integer id) {
        int userId;
        try {
            userId = authenticationService.getAuthenticatedUserId();
            return ResponseEntity.ok(userService.subscribe(userId, id));
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * End point that unsubscribe the authenticated user to a topic.
     *
     * @param id id of the topic.
     * @return a ResponseEntity containing a MessageResponse if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @DeleteMapping("/api/topics/{id}/subscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") final Integer id) {
        int userId;
        try {
            userId = authenticationService.getAuthenticatedUserId();
            return ResponseEntity.ok(userService.unsubscribe(userId, id));
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

