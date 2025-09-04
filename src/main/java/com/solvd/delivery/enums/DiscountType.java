package com.solvd.delivery.enums;

import com.solvd.delivery.exceptions.UnknownDiscountTypeException;

public enum DiscountType {
    PERCENTAGE(1, "Percentage"),
    FIXED_AMOUNT(2, "Fixed Amount"),
    FREE_SHIPPING(3, "Free Shipping");

    private final int id;
    private final String label;

    DiscountType(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }

    public static DiscountType fromLabel(String label) throws UnknownDiscountTypeException {
        if (label == null) {
            throw new UnknownDiscountTypeException("Label cannot be null");
        }

        switch (label.toLowerCase()) {
            case "percentage":
                return PERCENTAGE;
            case "fixed":
            case "fixed_amount":
                return FIXED_AMOUNT;
            case "free_shipping":
                return FREE_SHIPPING;
            default:
                throw new UnknownDiscountTypeException("Unknown DiscountType label: " + label);
        }
    }

    public static DiscountType fromId(int id) throws UnknownDiscountTypeException {
        for (DiscountType type : values()) {
            if (type.id == id) return type;
        }
        throw new UnknownDiscountTypeException("Unknown DiscountType id: " + id);
    }
}
