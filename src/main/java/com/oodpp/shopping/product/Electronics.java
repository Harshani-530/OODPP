package com.oodpp.shopping.product;

public class Electronics implements Product {
    private final String id;
    private final String name;
    private final double price;
    private final int warrantyMonths;

    public Electronics(String id, String name, double price, int warrantyMonths) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }
}
