package com.zen.lab.services.program.service;

import com.zen.lab.services.program.model.Event;

public interface LiveFeedProcessor {

    /**
     * Process a given Message from Kafka. Processing might throw a generic exception
     * @param key
     * @param message
     * @param offset
     * @throws Exception
     */
    void process(String key, Event message, long offset) throws Exception;
}
