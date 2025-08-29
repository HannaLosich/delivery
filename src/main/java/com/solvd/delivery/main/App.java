package com.solvd.delivery.main;

import com.solvd.delivery.models.*;
import com.solvd.delivery.enums.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {
    // Sample lists to hold created objects
    public List<User> User = new ArrayList<>();
    public List<Address> Address = new ArrayList<>();
    public List<Product> Product = new ArrayList<>();
    public List<Warehouse> Warehouse = new ArrayList<>();
    public List<Inventory> inventoryList = new ArrayList<>();
    public List<Order> Order = new ArrayList<>();
    public List<OrderItem> OrderItem = new ArrayList<>();
    public List<Payment> Payment = new ArrayList<>();
    public List<PaymentTransaction> transactions = new ArrayList<>();
    public List<Shipment> Shipment = new ArrayList<>();
    public List<Courier> Courier = new ArrayList<>();
    public List<Tracking> trackingList = new ArrayList<>();
    public List<Review> Review = new ArrayList<>();
    public List<SupportTicket> tickets = new ArrayList<>();
    public List<TicketMessage> TicketMessage = new ArrayList<>();
    public List<Promotion> Promotion = new ArrayList<>();
    public List<OrderPromotion> orderPromotion = new ArrayList<>();

    public App() {
        initData();
    }

    private void initData() {
        // User
        User user1 = new User(1L, "Alice", "Smith", "alice@example.com", "+1234567890");
        User user2 = new User(2L, "Bob", "Johnson", "bob@example.com", "+1987654321");
        User.add(user1);
        User.add(user2);

        // Address
        Address.add(new Address(1L, "123 Main St", "New York", "NY", "USA", "10001", 1L));
        Address.add(new Address(2L, "456 Market St", "San Francisco", "CA", "USA", "94105", 2L));

        // Product
        Product.add(new Product(1L, "Wireless Mouse", "Ergonomic mouse", 29.99, "MOUSE-001"));
        Product.add(new Product(2L, "Mechanical Keyboard", "RGB keyboard", 79.99, "KEYB-001"));

        // Warehouse
        Warehouse.add(new Warehouse(1L, "East Coast Warehouse", "New Jersey"));
        Warehouse.add(new Warehouse(2L, "West Coast Warehouse", "California"));

        // Inventory
        inventoryList.add(new Inventory(1L, 200, LocalDateTime.now(), 1L, 1L));
        inventoryList.add(new Inventory(2L, 150, LocalDateTime.now(), 2L, 2L));

        // Order
        Order.add(new Order(1L, LocalDateTime.now(), OrderStatus.PENDING, 109.98, 1L, 1L));
        Order.add(new Order(2L, LocalDateTime.now(), OrderStatus.SHIPPED, 29.99, 2L, 2L));

        // Order Items
        OrderItem.add(new OrderItem(1L, 2, 29.99, 1L, 1L));
        OrderItem.add(new OrderItem(2L, 1, 79.99, 1L, 2L));
        OrderItem.add(new OrderItem(3L, 1, 29.99, 2L, 1L));

        // Payment
        Payment.add(new Payment(1L, 109.98, PaymentMethod.CREDIT_CARD, LocalDateTime.now(), 1L));
        Payment.add(new Payment(2L, 29.99, PaymentMethod.PAYPAL, LocalDateTime.now(), 2L));

        // Payment Transactions
        transactions.add(new PaymentTransaction(1L, "TXN123ABC", LocalDateTime.now(), TransactionStatus.SUCCESS, 1L));
        transactions.add(new PaymentTransaction(2L, "TXN456XYZ", LocalDateTime.now(), TransactionStatus.SUCCESS, 2L));

        // Shipment
        Shipment.add(new Shipment(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(2), ShipmentStatus.DELAYED, 1L));
        Shipment.add(new Shipment(2L, LocalDateTime.now(), LocalDateTime.now().plusDays(3), ShipmentStatus.DELAYED, 2L));

        // Courier
        Courier.add(new Courier(1L, "FastExpress", "+1111111111", "support@fastexpress.com", 1L, 1L));
        Courier.add(new Courier(2L, "QuickShip", "+2222222222", "help@quickship.com", 2L, 2L));

        // Tracking
        trackingList.add(new Tracking(1L, "TRK123456", TrackingStatus.PROCESSING, LocalDateTime.now(), 1L));
        trackingList.add(new Tracking(2L, "TRK789012", TrackingStatus.PROCESSING, LocalDateTime.now(), 2L));

        // Review
        Review.add(new Review(1L, 5, "Great delivery service!", LocalDateTime.now(), 2L, 1L));

        // Support Tickets
        tickets.add(new SupportTicket (1L, "Order not delivered", TicketStatus.OPEN, TicketPriority.HIGH, LocalDateTime.now(), LocalDateTime.now(), 1L));
        tickets.add(new SupportTicket (2L, "Payment issue", TicketStatus.PROCESSING, TicketPriority.MEDIUM, LocalDateTime.now(), LocalDateTime.now(), 2L));

        // Ticket Messages
        TicketMessage.add(new TicketMessage(1L, "Where is my order?", LocalDateTime.now(), 1L));
        TicketMessage.add(new TicketMessage(2L, "My payment failed.", LocalDateTime.now(), 2L));

        // Promotion
        Promotion.add(new Promotion(1L, "SUMMER10", "10% off summer sale", DiscountType.PERCENTAGE, 10.0, LocalDateTime.of(2025,6,1,0,0), LocalDateTime.of(2025,8,31,23,59)));
        Promotion.add(new Promotion(2L, "WELCOME5", "5 USD off first order", DiscountType.FIXED_AMOUNT, 5.0, LocalDateTime.of(2025,1,1,0,0), LocalDateTime.of(2025,12,31,23,59)));

        // Order Promotion
        orderPromotion.add(new OrderPromotion(1L, 1L, 1L));
        orderPromotion.add(new OrderPromotion(2L, 2L, 2L));
    }
}
