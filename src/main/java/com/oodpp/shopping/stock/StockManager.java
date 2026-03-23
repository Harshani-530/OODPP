package com.oodpp.shopping.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockManager implements StockSubject {
    private final Map<String, Integer> quantitiesByProductId = new HashMap<>();
    private final Map<String, List<StockObserver>> observersByProductId = new HashMap<>();

    @Override
    public void subscribe(String productId, StockObserver observer) {
        observersByProductId
                .computeIfAbsent(productId, key -> new ArrayList<>())
                .add(observer);
    }

    @Override
    public void unsubscribe(String productId, StockObserver observer) {
        List<StockObserver> observers = observersByProductId.get(productId);
        if (observers != null) {
            observers.remove(observer);
        }
    }

    @Override
    public void restock(String productId, String productName, int addedQuantity) {
        if (addedQuantity <= 0) {
            throw new IllegalArgumentException("Restock quantity must be positive.");
        }

        int newQuantity = quantitiesByProductId.getOrDefault(productId, 0) + addedQuantity;
        quantitiesByProductId.put(productId, newQuantity);
        notifyObservers(productId, productName, newQuantity);
    }

    public int getQuantity(String productId) {
        return quantitiesByProductId.getOrDefault(productId, 0);
    }

    private void notifyObservers(String productId, String productName, int newQuantity) {
        List<StockObserver> observers = observersByProductId.getOrDefault(productId, List.of());
        for (StockObserver observer : observers) {
            observer.onRestocked(productId, productName, newQuantity);
        }
    }
}
