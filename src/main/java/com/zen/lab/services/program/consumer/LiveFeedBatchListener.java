package com.zen.lab.services.program.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.BatchAcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class LiveFeedBatchListener implements BatchAcknowledgingConsumerAwareMessageListener<String, String> {

    private final Logger LOGGER = Logger.getLogger(LiveFeedBatchListener.class.getName());

    @Override
    public void onMessage(List<ConsumerRecord<String, String>> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        for(ConsumerRecord cr : data) {
            LOGGER.info((String) cr.value());
        }
    }
}
