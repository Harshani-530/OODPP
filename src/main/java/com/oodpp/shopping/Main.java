package com.oodpp.shopping;

import com.oodpp.shopping.cart.ShoppingCart;
import com.oodpp.shopping.customer.Customer;
import com.oodpp.shopping.factory.BookFactory;
import com.oodpp.shopping.factory.ElectronicsFactory;
import com.oodpp.shopping.factory.ProductFactory;
import com.oodpp.shopping.product.Product;
import com.oodpp.shopping.stock.StockManager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final ProductFactory BOOK_FACTORY = new BookFactory();
    private static final ProductFactory ELECTRONICS_FACTORY = new ElectronicsFactory();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();
        StockManager stockManager = new StockManager();
        Map<String, Customer> customersByName = new LinkedHashMap<>();

        System.out.println("========================================");
        System.out.println("      Welcome to ShoppingCartApp");
        System.out.println("========================================");

        boolean addingProducts = true;
        while (addingProducts) {
            System.out.println();
            System.out.println("Select an option:");
            System.out.println("1) Add Book");
            System.out.println("2) Add Electronics");
            System.out.println("3) Checkout");

            int choice = readIntInRange(scanner, "Enter choice (1-3): ", 1, 3);
            if (choice == 3) {
                addingProducts = false;
                continue;
            }

            Product product = createProductInteractively(scanner, choice);
            cart.addProduct(product);

            System.out.printf("Added to cart: %s [%s] - $%.2f%n",
                    product.getName(),
                    product.getCategory(),
                    product.getPrice());

            boolean subscribe = readYesNo(scanner, "Subscribe a customer for restock alerts on this product? (yes/no): ");
            if (subscribe) {
                String customerName = readNonEmpty(scanner, "Enter customer name: ");
                Customer customer = customersByName.computeIfAbsent(customerName, Customer::new);
                stockManager.subscribe(product.getId(), customer);
                System.out.printf("%s subscribed to restock updates for %s (%s).%n",
                        customer.getName(),
                        product.getName(),
                        product.getId());
            }
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println("           Checkout Summary");
        System.out.println("========================================");
        System.out.println(cart.checkoutSummary());

        if (!customersByName.isEmpty()) {
            System.out.println();
            boolean runRestockDemo = readYesNo(scanner, "Trigger restock and send notifications now? (yes/no): ");
            if (runRestockDemo) {
                String productId = readNonEmpty(scanner, "Enter product ID to restock: ");
                String productName = readNonEmpty(scanner, "Enter product name: ");
                int quantity = readPositiveInt(scanner, "Enter quantity added: ");

                stockManager.restock(productId, productName, quantity);

                System.out.println();
                System.out.println("========================================");
                System.out.println("         Stock Notifications");
                System.out.println("========================================");
                customersByName.values().forEach(customer -> {
                    System.out.println("Notifications for " + customer.getName() + ":");
                    if (customer.getNotifications().isEmpty()) {
                        System.out.println("- No notifications received.");
                    } else {
                        customer.getNotifications().forEach(message -> System.out.println("- " + message));
                    }
                    System.out.println();
                });
            }
        }

        System.out.println("Thank you for using ShoppingCartApp!");
    }

    private static Product createProductInteractively(Scanner scanner, int choice) {
        String id = readNonEmpty(scanner, "Enter product ID: ");
        String name = readNonEmpty(scanner, "Enter product name: ");
        double price = readPositiveDouble(scanner, "Enter product price: ");

        if (choice == 1) {
            return BOOK_FACTORY.createProduct(id, name, price);
        }

        return ELECTRONICS_FACTORY.createProduct(id, name, price);
    }

    private static boolean readYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            }
            if (input.equals("no") || input.equals("n")) {
                return false;
            }
            System.out.println("Please enter yes or no.");
        }
    }

    private static String readNonEmpty(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    private static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.printf("Please enter a number between %d and %d.%n", min, max);
        }
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Please enter a positive integer.");
        }
    }

    private static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Please enter a positive number.");
        }
    }
}
