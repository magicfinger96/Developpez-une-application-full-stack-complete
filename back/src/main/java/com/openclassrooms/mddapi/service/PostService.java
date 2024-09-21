package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.dto.PostDto;
import com.openclassrooms.mddapi.model.entity.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service which handles posts logic.
 */
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

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
     * Creates a post.
     *
     * @param post post saved.
     */
    public void createPost(Post post) {
        postRepository.save(post);
    }
}
