package com.zen.lab.services.program.infra;

public class KafkaProperties {

    private KafkaProperties() {}

    public static final String TOPIC = "topic1";
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 5000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    public static final String TOPIC_LIVE_FEED = "live-sports-feed";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    public static final String TOPIC_FIXTURES = "fixtures";
    public static final String TOPIC_ROUND_ROBIN = "roundrobin";

}
