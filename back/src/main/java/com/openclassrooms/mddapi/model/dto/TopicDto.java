package com.openclassrooms.mddapi.model.dto;

import lombok.Data;

/**
 * DTO of a topic.
 */
@Data
public class TopicDto {
    int id;
    String title;
    String description;
    boolean subscribed;
}
