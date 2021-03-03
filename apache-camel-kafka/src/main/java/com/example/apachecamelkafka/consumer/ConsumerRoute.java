package com.example.apachecamelkafka.consumer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Taner YILDIRIM
 */
@Component
public class ConsumerRoute extends RouteBuilder {

    private static final String TOPIC = "kafka:{{kafka.topic}}?brokers={{kafka.server}}:{{kafka.port}}";
    private static final String GROUP_ID = "&groupId={{kafka.channel}}";
    private static final String CONSUMER_COUNT = "&consumersCount=5";
    private static final String CONSUMER_STREAMS = "&consumerStreams=10";
    private static final String MAX_POLL_COUNT = "&maxPollRecords=10";
    private static final String SESSION_TIMEOUT = "&sessionTimeoutMs=150000";
    private static final String SET_OFFSET = "&autoOffsetReset=earliest&consumerRequestTimeoutMs=200000";

    private static final String OBJECT_DESERIALIZE_CLASS = "&valueDeserializer=com.example.apachecamelkafka.serializer.ObjectDeserializer";

    @Autowired
    private Consumer consumer;

    @Override
    public void configure() throws Exception {

        from(TOPIC +
                GROUP_ID +
                CONSUMER_COUNT +
                CONSUMER_STREAMS +
                SET_OFFSET +
                SESSION_TIMEOUT +
                MAX_POLL_COUNT +
                OBJECT_DESERIALIZE_CLASS).process(consumer);
    }
}
