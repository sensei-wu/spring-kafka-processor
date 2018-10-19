package com.zen.lab.services.program.consumer;

import com.zen.lab.services.program.model.Event;
import com.zen.lab.services.program.service.LiveFeedProcessor;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.BatchAcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LiveFeedBatchListener implements BatchAcknowledgingConsumerAwareMessageListener<String, Event> {

    private final Logger LOGGER = LoggerFactory.getLogger(LiveFeedBatchListener.class.getName());

    private final LiveFeedProcessor liveFeedProcessor;

    @Autowired
    public LiveFeedBatchListener(LiveFeedProcessor liveFeedProcessor) {this.liveFeedProcessor = liveFeedProcessor;}

    @Override
    public void onMessage(List<ConsumerRecord<String, Event>> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        for(ConsumerRecord<String, Event> cr : data) {
            LOGGER.info(cr.value().toString());
            try {
                liveFeedProcessor.process(cr.key(), cr.value(), cr.offset());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
