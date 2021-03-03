package com.example.apachecamelkafka.consumer;

import com.example.apachecamelkafka.message.StockInfo;
import com.example.apachecamelkafka.message.StockMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Taner YILDIRIM
 */
@Component
public class Consumer implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        StockMessage message = exchange.getIn().getBody(StockMessage.class);

        for (StockInfo stockInfo : message.getStockInfoList()
             ) {
            System.out.println("Kafkadan okunan hisse senedi bilgisi : " + stockInfo);
        }

    }

    public void clearScreen() {
        System.out.print("\u001b[2J");
        System.out.flush();
    }
}
