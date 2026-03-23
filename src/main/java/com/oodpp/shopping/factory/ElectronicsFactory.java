package com.oodpp.shopping.factory;

import com.oodpp.shopping.product.Electronics;
import com.oodpp.shopping.product.Product;

public class ElectronicsFactory implements ProductFactory {
    @Override
    public Product createProduct(String id, String name, double price) {
        return new Electronics(id, name, price, 24);
    }
}
