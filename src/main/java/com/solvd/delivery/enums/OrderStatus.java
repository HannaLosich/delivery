package com.solvd.delivery.enums;

import com.solvd.delivery.exceptions.UnknownOrderStatusException;

public enum OrderStatus {
    PENDING(1, "Pending"),
    PAID(2, "Paid"),
    SHIPPED(3, "Shipped"),
    DELIVERED(4, "Delivered"),
    CANCELLED(5, "Cancelled"),
    COMPLETED(6, "Completed");

    private final int id;
    private final String label;

    OrderStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static OrderStatus fromLabel(String label) throws UnknownOrderStatusException {
        if (label == null) {
            throw new UnknownOrderStatusException("Label cannot be null");
        }

        switch (label.toLowerCase()) {
            case "pending": return PENDING;
            case "paid": return PAID;
            case "shipped": return SHIPPED;
            case "delivered": return DELIVERED;
            case "cancelled": return CANCELLED;
            case "completed": return COMPLETED;
            default:
                throw new UnknownOrderStatusException("Unknown OrderStatus label: " + label);
        }
    }
}
