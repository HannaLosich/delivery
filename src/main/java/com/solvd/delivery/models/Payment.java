package com.solvd.delivery.models;

import com.solvd.delivery.enums.PaymentMethod;
import java.time.LocalDateTime;

public class Payment {
    private long id;
    private double amount;
    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private long orderId;

    public Payment() {}

    public Payment(long id, double amount, PaymentMethod paymentMethod, LocalDateTime paymentDate, long orderId) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.orderId = orderId;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }
}
