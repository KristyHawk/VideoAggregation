package com.video.testtask.video_data_aggregation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Web client service that is used for sending requests using {@link RestTemplate}
 */
@Component(value = "webClientJsonResponseService")
public class WebClientJsonResponseService implements WebClientApi<String>{

    private final RestTemplate restTemplate;

    @Autowired
    public WebClientJsonResponseService(RestTemplate restTemplateClient) {
        this.restTemplate = restTemplateClient;
    }

    @Override
    public ResponseEntity<String> getResponse(String url) {
        return restTemplate.getForEntity(url, String.class);
    }
}
