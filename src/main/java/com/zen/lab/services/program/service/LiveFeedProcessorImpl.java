package com.zen.lab.services.program.service;

import com.zen.lab.services.program.infra.service.MetricService;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class LiveFeedProcessorImpl implements LiveFeedProcessor {

    @Value("${livefeed.processor.minSleep:200}")
    private int MIN_SLEEP_MS;
    @Value("${livefeed.processor.maxSleep:1000}")
    private int MAX_SLEEP_MS;
    @Value("${livefeed.processor.errorTimeOut:1000}")
    private int ERROR_TIMEOUT;

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveFeedProcessorImpl.class);

    private final MetricService metricService;

    private final static String METRICS_PROCESSED_SUCCESS_NAME = LiveFeedProcessorImpl.class.getSimpleName() + ".processed";
    private final static String METRICS_PROCESSED_ERROR_NAME = LiveFeedProcessorImpl.class.getSimpleName() + ".error";

    @Autowired
    public LiveFeedProcessorImpl(MetricService metricService) {
        this.metricService = metricService;
    }

    @Override
    //@Timed("livefeed.Processed.timer") // => doesn't work, under investigation
    public void process(String key, String message, long offset) throws Exception {

        LocalTime begin = LocalTime.now();
        long sleep = MIN_SLEEP_MS + (long) ((MAX_SLEEP_MS - MIN_SLEEP_MS) * ThreadLocalRandom.current().nextDouble(1.0d));
        LOGGER.trace("SLEEP {}: ", sleep);
        //simulate a latency => watchout the consumer lag meanwhile
        TimeUnit.MILLISECONDS.sleep(sleep);

        //simulate an exception
        if(offset%8 == 0) {
            //simulate a timeout => watchout the lag meanwhile
            TimeUnit.MILLISECONDS.sleep(ERROR_TIMEOUT);
            metricService.increaseCounter(METRICS_PROCESSED_ERROR_NAME);
            throw new Exception(String.format("Oops, we can't process messages with offset multiple of 8 -- offset %d, message %s", offset, message));
        }

        metricService.recordHistogram(METRICS_PROCESSED_SUCCESS_NAME,
                Duration.between(begin, LocalTime.now()),
                "sportType", key);

        LOGGER.info("Processed Message with offset {}: length {}, hashCode {}", offset, message.length(), message.hashCode());
    }
}
