package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.dto.PostDto;
import com.openclassrooms.mddapi.model.request.PostRequest;
import com.openclassrooms.mddapi.model.response.MessageResponse;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Handles the end points related to the posts.
 */
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthenticationService authenticationService;


    /**
     * End point that provides the feed of the authenticated user.
     * A feed being the posts which belongs to topics the user subscribed to.
     *
     * @param sort  the post data to sort.
     * @param order the sort order: ascending or descending.
     * @return a ResponseEntity containing the posts if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @Operation(summary = "Get the user feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the feed", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PostDto.class)))}),
            @ApiResponse(responseCode = "400", description = "The sort or order parameters are invalid", content = @Content),
            @ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
    @GetMapping("/api/posts")
    public ResponseEntity<PostDto[]> getFeed(
            @Parameter(description = "Field of the post to sort")
            @RequestParam(defaultValue = "creationDate") String sort,
            @Parameter(description = "The sort order")
            @RequestParam(defaultValue = "desc") String order) {
        try {
            int userId = authenticationService.getAuthenticatedUserId();

            Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
            return ResponseEntity.ok(postService.getFeed(userId, Sort.by(direction, sort)));

        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (InvalidDataAccessApiUsageException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * End point that create a new post from the authenticated user.
     *
     * @param postRequest data to create the new Post.
     * @return a ResponseEntity containing a MessageResponse if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @Operation(summary = "Create a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created the post", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Topic or user not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Input data are missing or not valid", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
    @PostMapping("/api/posts")
    public ResponseEntity<MessageResponse> createPost(@Valid @RequestBody PostRequest postRequest) {

        try {
            postService.createPost(postRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MessageResponse response = new MessageResponse("L'article a été créé !");
        return ResponseEntity.ok(response);
    }

    /**
     * End point that provides a post.
     *
     * @param id id of the post.
     * @return a ResponseEntity containing the post if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @Operation(summary = "Get a post by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the post", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))}),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") final Integer id) {
        Optional<PostDto> postDto = postService.getPostDtoById(id);
        return postDto.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

