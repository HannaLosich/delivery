package com.solvd.delivery.main;


public class Main {
    public static void main(String[] args) {
        // Initialize the app and all delivery data
        App app = new App();

        // Just a simple output to confirm objects are created
        System.out.println("Delivery Service App initialized with sample data.");
        System.out.println("Total User: " + app.User.size());
        System.out.println("Total Order: " + app.Order.size());
        System.out.println("Total Product: " + app.Product.size());
    }
}
