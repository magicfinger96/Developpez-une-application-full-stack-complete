package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the ModelMapper.
 */
@Configuration
public class ModelMapperConfig {

    public ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
