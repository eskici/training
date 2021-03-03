package com.example.apachekafka.stock;

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

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

}
