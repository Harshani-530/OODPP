package com.oodpp.shopping.stock;

public interface StockSubject {
    void subscribe(String productId, StockObserver observer);
    void unsubscribe(String productId, StockObserver observer);
    void restock(String productId, String productName, int addedQuantity);
}
