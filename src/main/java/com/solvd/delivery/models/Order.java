package com.solvd.delivery.models;

import com.solvd.delivery.enums.OrderStatus;
import java.time.LocalDateTime;

public class Order {
    private long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private double totalAmount;
    private long userId;
    private long addressId;

    public Order() {}

    public Order(long id, LocalDateTime orderDate, OrderStatus status, double totalAmount, long userId, long addressId) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.userId = userId;
        this.addressId = addressId;
    }


    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getAddressId() { return addressId; }
    public void setAddressId(long addressId) { this.addressId = addressId; }
}
