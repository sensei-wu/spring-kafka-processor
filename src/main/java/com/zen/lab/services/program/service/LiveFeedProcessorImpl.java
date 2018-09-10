package com.zen.lab.services.program.service;

import io.micrometer.core.instrument.MeterRegistry;
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

    @Value("$livefeed.processor.minSleep")
    private final static int MIN_SLEEP_MS = 200;
    @Value("$livefeed.processor.maxSleep")
    private final static int MAX_SLEEP_MS = 1000;
    @Value("$livefeed.processor.errorTimeOut")
    private final static int ERROR_TIMEOUT = 1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveFeedProcessorImpl.class);

    private final MeterRegistry meterRegistry;

    @Autowired
    public LiveFeedProcessorImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    //@Timed("livefeed.Processed.timer") // => doesn't work, under investigation
    public void process(String key, String message, long offset) throws Exception {

        LocalTime begin = LocalTime.now();

        //simulate a latency => watchout the consumer lag meanwhile
        TimeUnit.MILLISECONDS.sleep(MIN_SLEEP_MS + (MAX_SLEEP_MS - MIN_SLEEP_MS) * ThreadLocalRandom.current().nextInt(1));

        //simulate an exception
        if(offset%8 == 0) {
            //simulate a timeout => watchout the lag meanwhile
            TimeUnit.MILLISECONDS.sleep(ERROR_TIMEOUT);
            meterRegistry.counter("messages.processed.error").increment();
            throw new Exception(String.format("Oops, we can't process messages with offset multiple of 8 -- offset %d, message %s", offset, message));
        }

        LOGGER.info("Processed Message with offset {}: length {}, hashCode {}", offset, message.length(), message.hashCode());


        Timer timer = Timer.builder("messages.processed.timer")
                .tags("success", key)
                .register(meterRegistry);
        timer.record(Duration.between(begin, LocalTime.now()));
    }
}
