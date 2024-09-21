package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.model.dto.PostDto;
import com.openclassrooms.mddapi.model.entity.Post;
import com.openclassrooms.mddapi.model.entity.Topic;
import com.openclassrooms.mddapi.model.entity.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the ModelMapper.
 */
@Configuration
public class ModelMapperConfig {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(PostDto.class, Post.class).addMappings(mapper -> {
            mapper.skip(Post::setTopic);
        }).setPostConverter(context -> {
            PostDto postDto = context.getSource();
            Post post = context.getDestination();

            Topic topic = topicRepository.findById(postDto.getTopic_id())
                    .orElseThrow(() -> new IllegalArgumentException("Le thème n'existe pas."));
            post.setTopic(topic);

            User author = userRepository.findById(postDto.getAuthor_id())
                    .orElseThrow(() -> new IllegalArgumentException("L'auteur n'existe pas."));
            post.setAuthor(author);

            return post;
        });

        return modelMapper;
    }
}
