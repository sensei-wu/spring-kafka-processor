package com.zen.lab.services.program.service;

public interface LiveFeedProcessor {

    /**
     * Process a given Message from Kafka. Processing might throw a generic exception
     * @param key
     * @param message
     * @param offset
     * @throws Exception
     */
    void process(String key, String message, long offset) throws Exception;
}
