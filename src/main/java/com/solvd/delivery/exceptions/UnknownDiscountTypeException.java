package com.solvd.delivery.exceptions;

public class UnknownDiscountTypeException extends Exception {
    public UnknownDiscountTypeException(String message) {
        super(message);
    }
}