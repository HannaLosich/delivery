package com.solvd.delivery.exceptions;

public class UnknownTransactionStatusException extends Exception {
    public UnknownTransactionStatusException(String message) {
        super(message);
    }
}
