package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of the topics.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    public List<Topic> findAllByOrderByTitleAsc();
}