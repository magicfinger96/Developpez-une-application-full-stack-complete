package com.openclassrooms.mddapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/**
 * Entity of a topic.
 */
@Entity
@Table(name = "topic")
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String title;
    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date creationDate;
}
