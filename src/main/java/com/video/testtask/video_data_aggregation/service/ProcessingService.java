package com.video.testtask.video_data_aggregation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.testtask.video_data_aggregation.entity.*;
import com.video.testtask.video_data_aggregation.handlers.error.ExceptionMessages;
import com.video.testtask.video_data_aggregation.handlers.error.InvalidRequestJsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ProcessingService {

    private static final Logger logger = Logger.getLogger(ProcessingService.class.getName());
    private static final String STARTING_PROCESSING_MESSAGE = "Start processing...";

    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;

    private ConcurrentLinkedQueue<CameraElement> cameraElementsQueue;
    private ConcurrentLinkedQueue<ProcessedCameraElement> processedCameraElements;

    @Autowired
    public ProcessingService(WebClientService webClientService, ObjectMapper objectMapper) {
        this.webClientService = webClientService;
        this.objectMapper = objectMapper;
        processedCameraElements = new ConcurrentLinkedQueue<>();
    }

    /**
     * Runs processing of incoming cameras json data
     *
     * @param camerasInfo - camerasInfo as json
     * @return aggregated info about all the cameras from camerasInfo
     */
    public String processCameraList(String camerasInfo) throws InvalidRequestJsonException, IOException, InterruptedException {
        logger.info(STARTING_PROCESSING_MESSAGE);
        formInitialQueue(camerasInfo);
        runProcessing();

        return formResult();
    }

    /**
     * Read incoming JSON string and transform it into a queue of {@link CameraElement}s
     *
     * @param camerasInfo - initial json string
     * @throws InvalidRequestJsonException - in case initial json string can not be parsed
     */
    private void formInitialQueue(String camerasInfo) throws InvalidRequestJsonException {
        JavaType parametrizedQueueType = objectMapper.getTypeFactory()
                .constructParametricType(ConcurrentLinkedQueue.class, CameraElement.class);
        try {
            cameraElementsQueue = objectMapper.readValue(camerasInfo, parametrizedQueueType);
        } catch (JsonProcessingException e) {
            throw new InvalidRequestJsonException(e);
        }
    }

    /**
     * Start multithreading process concerning elements construction processing.
     * The goal is to transform input {@link CameraElement} object into output {@link ProcessedCameraElement} object.
     */
    private void runProcessing() throws InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        Thread[] threads = new Thread[availableProcessors];

        for (int i = 0; i < availableProcessors; i++) {
            Thread thread = new ProcessThread();
            threads[i] = thread;
            thread.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    /**
     * Forms the result of threads executions. Converts the resulting queue into json string.
     *
     * @return json as string
     * @throws IOException - in case it is impossible to write value with object mapper
     */
    private String formResult() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        objectMapper.writeValue(out, processedCameraElements);

        return new String(out.toByteArray());
    }

    class ProcessThread extends Thread {

        @Override
        public void run() {
            ProcessedCameraElement processedCameraElement;

            while (!cameraElementsQueue.isEmpty()) {
                CameraElement element = cameraElementsQueue.poll();

                final String sourceDataUrl = element.getSourceDataUrl();
                final String tokenDataUrl = element.getTokenDataUrl();

                String sourceDataAsJson = webClientService.getJsonResponse(sourceDataUrl);
                String tokenDataAsJson = webClientService.getJsonResponse(tokenDataUrl);

                SourceData sourceData;
                TokenData tokenData;

                try {
                    sourceData = objectMapper.readValue(sourceDataAsJson, SourceData.class);
                    tokenData = objectMapper.readValue(tokenDataAsJson, TokenData.class);
                } catch (JsonProcessingException jsonProcessingException) {
                    // we can form json error structure in this case,
                    // but for this we should come to agreement of such api cases
                    logger.log(Level.WARNING, String.format(ExceptionMessages.JSON_PROCESSING_ERROR_MESSAGE, element.getId()));
                    continue;
                }

                if (!checkIfUrlTypeValid(sourceData.getUrlType())) {
                    logger.log(Level.WARNING, String.format(ExceptionMessages.RECEIVED_UNCOMMON_URL_TYPE_DATA, element.getId()));
                    continue;
                }

                processedCameraElement = createNewProcessedCameraElement(element.getId(), sourceData, tokenData);

                processedCameraElements.add(processedCameraElement);
            }
        }

        private boolean checkIfUrlTypeValid(UrlType urlType) {
            return urlType == UrlType.LIVE || urlType == UrlType.ARCHIVE;
        }

        private ProcessedCameraElement createNewProcessedCameraElement(int id, SourceData sourceData, TokenData tokenData) {
            return new ProcessedCameraElement(id,
                    sourceData.getUrlType(),
                    sourceData.getVideoUrl(),
                    tokenData.getValue(),
                    tokenData.getTtl());
        }
    }
}
