package com.solvd.delivery.exceptions;

public class UnknownPaymentMethodException extends Exception {
    public UnknownPaymentMethodException(String message) {
        super(message);
    }
}