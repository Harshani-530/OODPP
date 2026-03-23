package com.oodpp.shopping;

import com.oodpp.shopping.customer.Customer;
import com.oodpp.shopping.factory.BookFactory;
import com.oodpp.shopping.factory.ElectronicsFactory;
import com.oodpp.shopping.factory.ProductFactory;
import com.oodpp.shopping.product.Product;
import com.oodpp.shopping.stock.StockManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShoppingCartSystemTest {

    @Test
    void factoryMethodCreatesExpectedProductTypes() {
        ProductFactory bookFactory = new BookFactory();
        ProductFactory electronicsFactory = new ElectronicsFactory();

        Product book = bookFactory.createProduct("B100", "Refactoring", 44.50);
        Product electronics = electronicsFactory.createProduct("E200", "Mechanical Keyboard", 129.00);

        assertEquals("Book", book.getCategory());
        assertEquals("Electronics", electronics.getCategory());
        assertEquals("Refactoring", book.getName());
    }

    @Test
    void observerNotifiesSubscribedCustomersOnRestock() {
        StockManager stockManager = new StockManager();
        Customer alice = new Customer("Alice");
        Customer bob = new Customer("Bob");

        stockManager.subscribe("E001", alice);
        stockManager.subscribe("E001", bob);

        stockManager.restock("E001", "Wireless Headset", 20);

        assertEquals(1, alice.getNotifications().size());
        assertEquals(1, bob.getNotifications().size());
        assertTrue(alice.getNotifications().get(0).contains("Wireless Headset"));
        assertEquals(20, stockManager.getQuantity("E001"));
    }
}
