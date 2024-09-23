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

        TopicDto[] topics = topicService.getTopics();
        try {
            int userId = authenticationService.getAuthenticatedUserId();
            for (int i = 0; i < topics.length; i++) {
                TopicDto topic = topics[i];
                boolean subscribed = userService.isSubscribedTo(userId, topic.getId());
                topic.setSubscribed(subscribed);
            }
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(topics);
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
        if (topicDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            int userId = authenticationService.getAuthenticatedUserId();
            boolean subscribed = userService.isSubscribedTo(userId, id);
            topicDto.get().setSubscribed(subscribed);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(topicDto.get());
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
            userService.subscribe(userId, id);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Votre abonnement a été ajouté !"));
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
            userService.unsubscribe(userId, id);

        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Votre abonnement a été retiré !"));
    }
}

