package com.example.apachekafka.producer;

import com.example.apachekafka.stock.StockInfo;
import com.example.apachekafka.stock.StockNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Taner YILDIRIM
 */

@Service
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, StockInfo> kafkaTemplate;

    private String kafkaTopic = "stock-exchange-topic";

    public void send() {

        for (StockNames stockName : StockNames.values()) {
            StockInfo stockInfo = new StockInfo(stockName.name(), new BigDecimal(123));
            kafkaTemplate.send(kafkaTopic, stockInfo);
        }
    }
}
