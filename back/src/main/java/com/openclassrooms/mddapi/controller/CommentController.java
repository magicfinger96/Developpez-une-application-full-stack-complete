package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.dto.CommentDto;
import com.openclassrooms.mddapi.model.entity.Post;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * End point that provides the comments of a post.
     *
     * @param postId id of the post the comments are retrieved from.
     * @return a ResponseEntity containing the comments if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @GetMapping("/api/comments")
    public ResponseEntity<CommentDto[]> getComments(@RequestParam("post_id") Integer postId) {
        Optional<Post> post = postService.getPostById(postId);
        return post.map(value -> ResponseEntity.ok(commentService.getComments(value))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
