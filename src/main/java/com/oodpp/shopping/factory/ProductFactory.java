package com.oodpp.shopping.factory;

import com.oodpp.shopping.product.Product;

public interface ProductFactory {
    Product createProduct(String id, String name, double price);
}
