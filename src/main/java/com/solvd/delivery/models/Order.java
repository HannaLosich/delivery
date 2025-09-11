package com.solvd.delivery.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.solvd.delivery.enums.OrderStatus;
import com.solvd.delivery.utils.LocalDateTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true) // ✅ Ignore extra fields in JSON (_comment, etc.)
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

    // Default constructor required by JAXB & Jackson
    public Order() {}

    public Order(long id, LocalDateTime orderDate, OrderStatus status, double totalAmount, long userId, long addressId) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.userId = userId;
        this.addressId = addressId;
    }

    // ===== Jackson-friendly Getters/Setters =====

    @JsonGetter("id")
    public long getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonGetter("orderDate")
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    @JsonSetter("orderDate")
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @JsonGetter("status")
    public OrderStatus getStatus() {
        return status;
    }

    @JsonSetter("status")
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @JsonGetter("totalAmount")
    public double getTotalAmount() {
        return totalAmount;
    }

    @JsonSetter("totalAmount")
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @JsonGetter("userId")
    public long getUserId() {
        return userId;
    }

    @JsonSetter("userId")
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @JsonGetter("addressId")
    public long getAddressId() {
        return addressId;
    }

    @JsonSetter("addressId")
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
