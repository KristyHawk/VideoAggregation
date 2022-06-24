package com.video.testtask.video_data_aggregation.handlers.response;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ServiceError {

    private String errorMessage;
    private HttpStatus status;
    private Date timeStamp;

    public ServiceError(String errorMessage, HttpStatus status, Date timeStamp) {
        this.errorMessage = errorMessage;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
