package com.video.testtask.video_data_aggregation.service;

import org.springframework.http.ResponseEntity;

public interface WebClientApi<T> {

    ResponseEntity<T> getResponse(String url);
}
