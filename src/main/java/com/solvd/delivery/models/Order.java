package com.solvd.delivery.models;

import com.solvd.delivery.enums.OrderStatus;
import com.solvd.delivery.utils.LocalDateTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

    @XmlAttribute
    private long id;

    @XmlElement(name = "orderDate")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime orderDate;

    @XmlElement(name = "status")
    private OrderStatus status;

    @XmlElement(name = "totalAmount")
    private double totalAmount;

    @XmlElement(name = "userId")
    private long userId;

    @XmlElement(name = "addressId")
    private long addressId;

    // Default constructor required by JAXB
    public Order() {}

    public Order(long id, LocalDateTime orderDate, OrderStatus status, double totalAmount, long userId, long addressId) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.userId = userId;
        this.addressId = addressId;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", userId=" + userId +
                ", addressId=" + addressId +
                '}';
    }
}
