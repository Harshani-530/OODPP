package com.oodpp.shopping.customer;

import com.oodpp.shopping.stock.StockObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer implements StockObserver {
    private final String name;
    private final List<String> notifications = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onRestocked(String productId, String productName, int newQuantity) {
        notifications.add("Hi " + name + ", " + productName + " (" + productId + ") is now in stock: " + newQuantity + " units.");
    }

    public List<String> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }
}
