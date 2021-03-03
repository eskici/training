package com.example.apachekafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Taner YILDIRIM
 */
@RestController
@RequestMapping(value = "/apache-kafka/")
public class KafkaWebController {

    @Autowired
    KafkaSender kafkaSender;

    @GetMapping(value = "/producer")
    public String producer(@RequestParam("message") String message) {
        kafkaSender.send();

        return "Message sent to the Kafka Topic java_in_use_topic Successfully";
    }
}
