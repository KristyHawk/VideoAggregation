package com.video.testtask.video_data_aggregation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Bean(name = "restTemplateClient")
    @Scope("singleton")
    public RestTemplate getRestClient() {
        return new RestTemplate();
    }
}
