package com.zen.lab.services.program.service;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class LiveFeedProcessorImpl implements LiveFeedProcessor {

    private final static int MIN_SLEEP_MS = 1500;
    private final static int MAX_SLEEP_MS = 2000;

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveFeedProcessorImpl.class);

    @Override
    @Timed("livefeed.Processed.timer")
    public void process(String message, long offset) throws Exception {

        //simulate a latency => watchout the consumer lag meanwhile
        TimeUnit.MILLISECONDS.sleep(MIN_SLEEP_MS + (MAX_SLEEP_MS - MIN_SLEEP_MS) * ThreadLocalRandom.current().nextInt(1));

        //simulate an exception
        if(offset%8 == 0) {
            //simulate a timeout => watchout the lag meanwhile
            TimeUnit.SECONDS.sleep(10);
            throw new Exception(String.format("Oops, we can't process messages with offset multiple of 8 -- offset %d, message %s", offset, message));
        }

        LOGGER.info("Processed Message with offset {}: length {}, hashCode {}", offset, message.length(), message.hashCode());
    }
}
