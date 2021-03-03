package com.example.apachecamelkafka.producer;

import com.example.apachecamelkafka.message.StockInfo;
import com.example.apachecamelkafka.message.StockMessage;
import com.example.apachecamelkafka.message.StockNames;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Taner YILDIRIM
 */
@Component
public class Producer implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        StockMessage message = new StockMessage();

        for (StockNames stockName : StockNames.values()) {
            StockInfo stockInfo = new StockInfo(stockName.name(), randomPrice());
            message.addStock(stockInfo);
        }
        System.out.println("Hisse fiyatları Kafkaya gönderiliyor");
        exchange.getIn().setBody(message);
    }

    private static BigDecimal randomPrice() {
        return new BigDecimal(random(1, 200));
    }

    private static int random(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
