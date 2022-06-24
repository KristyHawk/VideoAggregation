package com.video.testtask.video_data_aggregation.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class RequestHelper {

    /**
     * Creates {@link HttpHeaders} with content-type of application/json
     *
     * @return new {@link HttpHeaders} object with "content-type" key and "application/json" value in it
     */
    public static HttpHeaders buildJsonHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return headers;
    }
}
