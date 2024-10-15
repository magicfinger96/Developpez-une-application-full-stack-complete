package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.dto.CommentDto;
import com.openclassrooms.mddapi.model.entity.Comment;
import com.openclassrooms.mddapi.model.entity.Post;
import com.openclassrooms.mddapi.model.entity.User;
import com.openclassrooms.mddapi.model.request.CommentRequest;
import com.openclassrooms.mddapi.model.response.MessageResponse;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Handles the end points related to the comments.
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    /**
     * End point that provides the comments of a post.
     *
     * @param postId id of the post the comments are retrieved from.
     * @return a ResponseEntity containing the comments if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @Operation(summary = "Get all the comments of a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the comments", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentDto.class)))}),
            @ApiResponse(responseCode = "404", description = "The post was not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
    @GetMapping("/api/comments")
    public ResponseEntity<CommentDto[]> getComments(
            @Parameter(description = "ID of the post to get comments for", required = true)
            @RequestParam("post_id") Integer postId) {
        Optional<Post> post = postService.getPostById(postId);
        return post.map(value -> ResponseEntity.ok(commentService.getComments(value))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * End point that create a new comment on a post from the authenticated user.
     *
     * @param commentRequest contains the data to create a comment.
     * @return a ResponseEntity containing a MessageResponse if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @Operation(summary = "Create a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created the comment", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Input data are missing or not valid", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "404", description = "The post or user is missing", content = @Content),
            @ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
    @PostMapping("/api/comments")
    public ResponseEntity<MessageResponse> createComment(@Valid @RequestBody CommentRequest commentRequest) {

        try {
            int userId = authenticationService.getAuthenticatedUserId();
            Optional<User> user = userService.getUserById(userId);
            Optional<Post> post = postService.getPostById(commentRequest.getPostId());

            if (user.isEmpty() || post.isEmpty()) {
                throw new Exception();
            }

            Comment comment = new Comment();
            comment.setAuthor(user.get());
            comment.setMessage(commentRequest.getMessage());
            comment.setPost(post.get());

            commentService.createComment(comment);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MessageResponse response = new MessageResponse("Le commentaire a été ajouté !");
        return ResponseEntity.ok(response);
    }
}
