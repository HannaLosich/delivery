package com.solvd.delivery.models;

public class OrderItem {
    private long id;
    private int quantity;
    private double unitPrice;
    private long orderId;
    private long productId;

    public OrderItem() {}

    public OrderItem(long id, int quantity, double unitPrice, long orderId, long productId) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.orderId = orderId;
        this.productId = productId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }
}

