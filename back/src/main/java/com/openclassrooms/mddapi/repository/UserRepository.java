package com.openclassrooms.mddapi.repository;


import com.openclassrooms.mddapi.model.entity.Topic;
import com.openclassrooms.mddapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository of the users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);

    public User findByUsername(String username);

    /**
     * Find the topic subscribed by the user.
     *
     * @param userId id of the user.
     * @return a list of Topic.
     */
    @Query("SELECT u.subscriptions FROM User u WHERE u.id = :userId")
    List<Topic> findSubscribedTopics(@Param("userId") int userId);
}