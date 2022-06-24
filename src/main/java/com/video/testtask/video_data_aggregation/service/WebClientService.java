package com.video.testtask.video_data_aggregation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Web client service that is used for sending requests using {@link RestTemplate}
 */
@Component
public class WebClientService {

    private final RestTemplate restTemplate;

    @Autowired
    public WebClientService(RestTemplate restTemplateClient) {
        this.restTemplate = restTemplateClient;
    }

    public String getJsonResponse(String url) {
        return restTemplate.getForEntity(url, String.class).getBody();
    }
}
