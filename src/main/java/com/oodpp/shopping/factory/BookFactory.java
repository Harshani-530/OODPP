package com.oodpp.shopping.factory;

import com.oodpp.shopping.product.Book;
import com.oodpp.shopping.product.Product;

public class BookFactory implements ProductFactory {
    @Override
    public Product createProduct(String id, String name, double price) {
        return new Book(id, name, price, "Unknown Author");
    }
}
