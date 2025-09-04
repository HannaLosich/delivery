package com.solvd.delivery.exceptions;

public class UnknownShipmentStatusException extends Exception {
    public UnknownShipmentStatusException(String message) {
        super(message);
    }
}
