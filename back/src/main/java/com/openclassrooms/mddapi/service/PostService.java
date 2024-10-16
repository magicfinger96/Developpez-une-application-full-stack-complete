package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.dto.PostDto;
import com.openclassrooms.mddapi.model.entity.Post;
import com.openclassrooms.mddapi.model.entity.Topic;
import com.openclassrooms.mddapi.model.entity.User;
import com.openclassrooms.mddapi.model.request.PostRequest;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Service which handles posts logic.
 */
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    /**
     * Get the feed of the user: all the posts belonging to topics the user is subscribed to.
     *
     * @param userId id of the user.
     * @param sort   object that sorts the result.
     * @return an array of PostDto.
     */
    public PostDto[] getFeed(int userId, Sort sort) {
        Collection<Post> posts = postRepository.findPostsFromSubscribedTopicByUser(userId, sort);
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList().toArray(PostDto[]::new);
    }

    /**
     * Get a post DTO.
     *
     * @param id id of the fetched post.
     * @return a post dto.
     */
    public Optional<PostDto> getPostDtoById(int id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(modelMapper.map(post, PostDto.class));
    }

    /**
     * Get a post.
     *
     * @param id id of the fetched post.
     * @return a post.
     */
    public Optional<Post> getPostById(int id) {
        return postRepository.findById(id);
    }

    /**
     * Creates a post.
     *
     * @param postRequest data to create a post.
     */
    public void createPost(PostRequest postRequest) throws Exception {

        int id = authenticationService.getAuthenticatedUserId();
        Optional<User> user = userService.getUserById(id);
        Optional<Topic> topic = topicService.getTopicById(postRequest.getTopicId());

        if (user.isEmpty() || topic.isEmpty()) {
            throw new Exception();
        }

        Post post = new Post();
        post.setAuthor(user.get());
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setTopic(topic.get());

        postRepository.save(post);
    }
}
