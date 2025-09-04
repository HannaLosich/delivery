package com.solvd.delivery.enums;

import com.solvd.delivery.exceptions.UnknownTransactionStatusException;
import java.util.Arrays;

public enum TransactionStatus {
    SUCCESS(1, "Success"),
    FAILED(2, "Failed"),
    PENDING(3, "Pending");

    private final int id;
    private final String label;

    TransactionStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TransactionStatus fromLabel(String label) throws UnknownTransactionStatusException {
        return Arrays.stream(TransactionStatus.values())
                .filter(s -> s.label.equalsIgnoreCase(label) || s.name().equalsIgnoreCase(label))
                .findFirst()
                .orElseThrow(() -> new UnknownTransactionStatusException("Unknown TransactionStatus label: " + label));
    }

    public static TransactionStatus fromId(int id) throws UnknownTransactionStatusException {
        return Arrays.stream(TransactionStatus.values())
                .filter(s -> s.id == id)
                .findFirst()
                .orElseThrow(() -> new UnknownTransactionStatusException("Unknown TransactionStatus id: " + id));
    }
}
