package com.example.apachecamelkafka.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taner YILDIRIM
 */
public class StockMessage implements Serializable {
    private List<StockInfo> stockInfoList = new ArrayList<>();

    public void addStock(StockInfo stockInfo) {
        stockInfoList.add(stockInfo);
    }

    public List<StockInfo> getStockInfoList() {
        return stockInfoList;
    }
}
