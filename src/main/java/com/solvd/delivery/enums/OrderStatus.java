package com.solvd.delivery.enums;

public enum OrderStatus {
    PENDING(1, "Pending"),
    PAID(2, "Paid"),
    SHIPPED(3, "Shipped"),
    DELIVERED(4, "Delivered"),
    CANCELLED(5, "Cancelled");

    private final int id;
    private final String label;

    OrderStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }

    public String getLabel() { return label; }

    public static OrderStatus fromLabel(String label) {
        if (label == null) throw new IllegalArgumentException("Label is null");
        switch (label.toLowerCase()) {
            case "pending": return PENDING;
            case "paid": return PAID;
            case "shipped": return SHIPPED;
            case "delivered": return DELIVERED;
            case "cancelled": return CANCELLED;
            default: throw new IllegalArgumentException("Unknown OrderStatus label: " + label);
        }
    }
}
