package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.model.dto.CommentDto;
import com.openclassrooms.mddapi.model.entity.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the ModelMapper.
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Comment.class, CommentDto.class).addMappings(mapper -> {
            mapper.skip(CommentDto::setAuthor);
        }).setPostConverter(context -> {
            Comment comment = context.getSource();
            CommentDto commentDto = context.getDestination();
            commentDto.setAuthor(comment.getAuthor().getUsername());
            return commentDto;
        });

        return modelMapper;
    }
}
