package com.video.testtask.video_data_aggregation.handlers;

import com.video.testtask.video_data_aggregation.controller.VideoAggregationController;
import com.video.testtask.video_data_aggregation.handlers.error.InvalidRequestJsonException;
import com.video.testtask.video_data_aggregation.handlers.response.ServiceError;
import com.video.testtask.video_data_aggregation.helper.RequestHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class ServiceExceptionHandler {

    private final Logger logger = Logger.getLogger(VideoAggregationController.class.getName());

    /**
     * Handles the case if the server received incorrect json input
     *
     * @return - response entity with 400 status and points to the cause of the exception
     */
    @ExceptionHandler(InvalidRequestJsonException.class)
    public ResponseEntity<ServiceError> handleInvalidRequestJsonError(InvalidRequestJsonException exception) {
        logger.log(Level.SEVERE, exception.getMessage() + "\n" + exception.getCause().getMessage());

        return formError(exception, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles internal server errors
     *
     * @param exception - internal server exception
     * @return response entity with 500 status
     */
    @ExceptionHandler({IOException.class, InterruptedException.class})
    public ResponseEntity<ServiceError> handleException(Exception exception) {
        logger.log(Level.SEVERE, exception.getMessage());

        return formError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Forms error response from service according to {@link ServiceError} template
     *
     * @param exception - exception the message of which should be included in a response
     * @param status    - status to send in a response
     * @return response entity with error information
     */
    private ResponseEntity<ServiceError> formError(Exception exception, HttpStatus status) {
        ServiceError serviceError = new ServiceError(exception.getMessage(), status, new Date());

        return new ResponseEntity<>(serviceError, RequestHelper.buildJsonHttpHeader(), serviceError.getStatus());
    }
}
