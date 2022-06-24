package com.video.testtask.video_data_aggregation.handlers.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestJsonException extends Exception {

    public InvalidRequestJsonException(Throwable cause) {
        super(ExceptionMessages.INCORRECT_JSON_ERROR_MESSAGE, cause);
    }
}
