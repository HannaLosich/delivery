package com.solvd.delivery.exceptions;

public class UnknownOrderStatusException extends Exception {
    public UnknownOrderStatusException(String message) {
        super(message);
    }
}
