package com.example.apachecamelkafka.producer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Taner YILDIRIM
 */
@Component
public class ProducerRoute extends RouteBuilder {

    private static final String TOPIC = "kafka:{{kafka.topic}}?brokers={{kafka.server}}:{{kafka.port}}";
    private static final String SESSION_TIMEOUT = "&sessionTimeoutMs=150000";
    private static final String SET_OFFSET = "&autoOffsetReset=earliest&consumerRequestTimeoutMs=200000";

    private static final String OBJECT_SERIALIZE_CLASS = "&serializerClass=com.example.apachecamelkafka.serializer.ObjectSerializer";

    @Autowired
    private Producer producer;

    @Override
    public void configure() throws Exception {

        from("timer://produceMessages?fixedRate=true&period=1000")
       //         from("direct:ProduceMessage")
                .process(producer).
                to(TOPIC +
                        SET_OFFSET +
                        SESSION_TIMEOUT +
                        OBJECT_SERIALIZE_CLASS);
    }
}
