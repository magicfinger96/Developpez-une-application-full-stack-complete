package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.dto.TopicDto;
import com.openclassrooms.mddapi.model.response.MessageResponse;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all the topics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the topics", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TopicDto.class)))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
    @GetMapping("/api/topics")
    public ResponseEntity<TopicDto[]> getTopics() {

        TopicDto[] topics = topicService.getTopics();
        try {
            int userId = authenticationService.getAuthenticatedUserId();
            for (TopicDto topic : topics) {
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
    @Operation(summary = "Get a topic by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the topic", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TopicDto.class))}),
            @ApiResponse(responseCode = "404", description = "Topic or user not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
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
    @Operation(summary = "Subscribe to a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscribed to the topic", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Topic or user not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "400", description = "The user is already subscribed to this topic", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
    @PostMapping("/api/topics/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable("id") final Integer id) {
        int userId;
        try {
            userId = authenticationService.getAuthenticatedUserId();
            userService.subscribe(userId, id);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
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
    @Operation(summary = "Unsubscribe from a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unsubscribed from the topic", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Topic or user not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "400", description = "The user is already unsubscribed from this topic", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
    @DeleteMapping("/api/topics/{id}/subscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") final Integer id) {
        int userId;
        try {
            userId = authenticationService.getAuthenticatedUserId();
            userService.unsubscribe(userId, id);

        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Votre abonnement a été retiré !"));
    }
}

