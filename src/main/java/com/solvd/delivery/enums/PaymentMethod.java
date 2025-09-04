package com.solvd.delivery.enums;

import com.solvd.delivery.exceptions.UnknownPaymentMethodException;

public enum PaymentMethod {
    CREDIT_CARD(1, "Credit Card"),
    PAYPAL(2, "PayPal"),
    BANK_TRANSFER(3, "Bank Transfer"),
    CASH_ON_DELIVERY(4, "Cash on Delivery");

    private final int id;
    private final String label;

    PaymentMethod(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }

    public String getLabel() { return label; }

    public static PaymentMethod fromLabel(String label) throws UnknownPaymentMethodException {
        switch (label.toLowerCase()) {
            case "credit card":
            case "credit_card":
                return CREDIT_CARD;

            case "paypal":
                return PAYPAL;

            case "bank transfer":
            case "bank_transfer":
                return BANK_TRANSFER;

            case "cash on delivery":
            case "cash_on_delivery":
                return CASH_ON_DELIVERY;

            default:
                throw new UnknownPaymentMethodException("Unknown PaymentMethod label: " + label);
        }
    }
}
