package com.oodpp.shopping.product;

public class Book implements Product {
    private final String id;
    private final String name;
    private final double price;
    private final String author;

    public Book(String id, String name, double price, String author) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
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
        return "Book";
    }

    public String getAuthor() {
        return author;
    }
}
