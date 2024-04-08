package com.pruebas.library.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining a ModelMapper bean with custom configuration.
 */
@Configuration
public class MapperConfig {

    /**
     * Bean definition for ModelMapper with custom configuration.
     *
     * @return ModelMapper instance configured with a loose matching strategy
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
