package com.zen.lab.services.program.consumer;

import com.zen.lab.services.program.service.LiveFeedProcessor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class LiveFeedMessageListener implements MessageListener<String, String> {

    private static Logger LOGGER = LoggerFactory.getLogger(LiveFeedMessageListener.class);

    private final LiveFeedProcessor liveFeedProcessor;

    @Autowired
    public LiveFeedMessageListener(LiveFeedProcessor liveFeedProcessor) {this.liveFeedProcessor = liveFeedProcessor;}

    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        LOGGER.info("Received a message {} ", data);
        try {
            liveFeedProcessor.process(data.value(), data.offset());
        } catch (Exception e) {
            LOGGER.error("Caught exception for offset {}", data.offset());
        }
    }
}
