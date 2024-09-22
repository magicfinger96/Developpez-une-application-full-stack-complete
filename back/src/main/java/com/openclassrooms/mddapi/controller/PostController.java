package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.dto.PostDto;
import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.model.entity.Post;
import com.openclassrooms.mddapi.model.response.MessageResponse;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * End point that provides the feed of the authenticated user.
     * A feed being the posts which belongs to topics the user subscribed to.
     *
     * @param sort  the post data to sort.
     * @param order the sort order: ascending or descending.
     * @return a ResponseEntity containing the posts if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @GetMapping("/api/posts")
    public ResponseEntity<PostDto[]> getFeed(@RequestParam(defaultValue = "createdAt") String sort,
                                             @RequestParam(defaultValue = "desc") String order) {
        try {
            int userId = authenticationService.getAuthenticatedUserId();

            Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
            return ResponseEntity.ok(postService.getFeed(userId, Sort.by(direction, sort)));

        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * End point that create a new post from the authenticated user.
     *
     * @param postDto data representing the new Post.
     * @return a ResponseEntity containing a MessageResponse if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @PostMapping("/api/posts")
    public ResponseEntity<MessageResponse> createPost(@Valid @RequestBody PostDto postDto) {

        try {
            int id = authenticationService.getAuthenticatedUserId();
            Optional<UserDto> user = userService.getUserDtoById(id);
            if (user.isEmpty()) {
                throw new Exception();
            }

            postDto.setAuthor(user.get());
            Post post = modelMapper.map(postDto, Post.class);
            postService.createPost(post);
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
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") final Integer id) {
        Optional<PostDto> postDto = postService.getPostDtoById(id);
        return postDto.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

