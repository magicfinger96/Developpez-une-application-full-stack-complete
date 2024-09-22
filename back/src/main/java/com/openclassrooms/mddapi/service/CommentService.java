package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.dto.CommentDto;
import com.openclassrooms.mddapi.model.entity.Comment;
import com.openclassrooms.mddapi.model.entity.Post;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service which handles comments logic.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get the comments of a post.
     *
     * @param post the post the comments belongs to.
     * @return an array of comment dto.
     */
    public CommentDto[] getComments(Post post) {
        Collection<Comment> comments = commentRepository.findByPostOrderByCreationDateAsc(post);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).toList().toArray(CommentDto[]::new);
    }
}
