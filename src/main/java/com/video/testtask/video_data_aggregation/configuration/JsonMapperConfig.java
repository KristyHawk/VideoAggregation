package com.video.testtask.video_data_aggregation.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class JsonMapperConfig {

    @Bean(name = "jsonObjectMapper")
    @Scope("singleton")
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
