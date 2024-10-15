package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of the posts.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


    /**
     * Find all the posts which have topic the user is subscribed to.
     *
     * @param userId             id of the user.
     * @param sort object that sorts the result.
     * @return a list of Post.
     */
    @Query("SELECT p FROM Post p " +
            "JOIN p.topic t " +
            "JOIN Subscription s ON t.id = s.topic.id " +
            "JOIN User u ON s.user.id = u.id " +
            "WHERE u.id = :userId ")
    List<Post> findPostsFromSubscribedTopicByUser(@Param("userId") int userId, Sort sort);
}