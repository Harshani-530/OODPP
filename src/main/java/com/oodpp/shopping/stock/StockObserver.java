package com.oodpp.shopping.stock;

public interface StockObserver {
    void onRestocked(String productId, String productName, int newQuantity);
}
