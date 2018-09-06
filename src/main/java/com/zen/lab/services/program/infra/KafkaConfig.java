package com.zen.lab.services.program.infra;

import com.zen.lab.services.program.consumer.LiveFeedBatchListener;
import com.zen.lab.services.program.consumer.LiveFeedMessageListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    private final LiveFeedMessageListener liveFeedMessageListener;

    private final LiveFeedBatchListener liveFeedBatchListener;

    private @Value("${messageListener.implementation}") String messageListenerImplementation;

    @Autowired
    public KafkaConfig(LiveFeedMessageListener liveFeedMessageListener, LiveFeedBatchListener liveFeedBatchListener) {
        this.liveFeedMessageListener = liveFeedMessageListener;
        this.liveFeedBatchListener = liveFeedBatchListener;
    }

    //In-order to use @KafkaListener, we need a bean with the name kafkaListenerContainerFactory
    @Bean
    @ConditionalOnProperty(value = "pojoListener.enabled")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    ConcurrentMessageListenerContainer<String, String> sportsFeedListenerContainer() throws Exception {
        ConsumerFactory<String, String> cf = consumerFactory();
        ContainerProperties cp = new ContainerProperties(KafkaProperties.TOPIC_LIVE_FEED);

        switch (messageListenerImplementation) {
            case "messageListener":
                cp.setMessageListener(liveFeedMessageListener);
                cp.setAckMode(AbstractMessageListenerContainer.AckMode.RECORD);
                break;
            case "batchListener":
                cp.setMessageListener(liveFeedBatchListener);
                break;
            default:
                throw new Exception("No listener configured");
        }

        ConcurrentMessageListenerContainer<String, String> concurrentMessageListenerContainer = new ConcurrentMessageListenerContainer<>(cf,cp);
        concurrentMessageListenerContainer.setConcurrency(3);

        return concurrentMessageListenerContainer;
    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), stringKeyDeserializer(), stringKeyDeserializer());
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "sportsdata.consumer");
        return props;
    }

    @Bean
    public Deserializer<String> stringKeyDeserializer() {
        return new StringDeserializer();
    }
}
