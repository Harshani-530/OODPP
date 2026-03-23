package com.oodpp.shopping.cart;

import com.oodpp.shopping.product.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart {
    private final List<Product> items = new ArrayList<>();

    public void addProduct(Product product) {
        items.add(product);
    }

    public List<Product> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public String checkoutSummary() {
        StringBuilder summary = new StringBuilder("Checkout Summary\n");
        for (Product product : items) {
            summary.append("- ")
                    .append(product.getName())
                    .append(" [")
                    .append(product.getCategory())
                    .append("]: $")
                    .append(String.format("%.2f", product.getPrice()))
                    .append("\n");
        }

        summary.append("Total: $")
                .append(String.format("%.2f", getTotalPrice()));

        return summary.toString();
    }
}
