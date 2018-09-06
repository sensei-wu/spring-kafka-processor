package com.zen.lab.services.program.service;

public interface LiveFeedProcessor {

    /**
     * Process a given Message from Kafka. Processing might thriw a generic exception
     * @param message
     * @param offset
     * @throws Exception
     */
    void process(String message, long offset) throws Exception;
}
