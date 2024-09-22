package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.entity.Comment;
import com.openclassrooms.mddapi.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of the comments.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    public List<Comment> findByPostOrderByCreationDateAsc(Post post);

}
