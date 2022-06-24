package com.video.testtask.video_data_aggregation.handlers.error;

public class ExceptionMessages {

    public static final String JSON_PROCESSING_ERROR_MESSAGE = "There was an error while parsing json response " +
            "of the camera with id %s";

    public static final String INCORRECT_JSON_ERROR_MESSAGE = "Incorrect request. Invalid json data";

    public static final String RECEIVED_UNCOMMON_URL_TYPE_DATA = "Received uncommon URL type data when validating" +
            " source information of the camera with id %s";

}
