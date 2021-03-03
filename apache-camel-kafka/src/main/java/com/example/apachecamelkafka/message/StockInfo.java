package com.example.apachecamelkafka.message;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Taner YILDIRIM
 */

public class StockInfo implements Serializable {

    private String stockName;

    private BigDecimal stockPrice;

    public StockInfo(String stockName, BigDecimal stockPrice) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
    }

    @Override
    public String toString() {
        return "StockInfo{" +
                "stockName='" + stockName + '\'' +
                ", stockPrice=" + stockPrice +
                '}';
    }
}
