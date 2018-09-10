package com.zen.lab.services.program.consumer;

import com.zen.lab.services.program.infra.KafkaProperties;
import com.zen.lab.services.program.service.LiveFeedProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@ConditionalOnBean(type = "kafkaListenerContainerFactory")
@Service
public class LiveFeedPojoListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveFeedPojoListener.class);

    private final LiveFeedProcessor liveFeedProcessor;

    @Autowired(required = false)
    public LiveFeedPojoListener(LiveFeedProcessor liveFeedProcessor) {this.liveFeedProcessor = liveFeedProcessor;}

    @KafkaListener(topics = KafkaProperties.TOPIC_LIVE_FEED)
    public void listen(String message,
                       @Header(KafkaHeaders.OFFSET) Integer offset,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partitionId,
                       @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        LOGGER.info("Processing Message -- Partition {}, Offset {}, Key {}", partitionId, offset, messageKey);

        try {
            liveFeedProcessor.process(messageKey, message, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
