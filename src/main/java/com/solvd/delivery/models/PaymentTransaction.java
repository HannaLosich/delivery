package com.solvd.delivery.models;

import com.solvd.delivery.enums.TransactionStatus;
import java.time.LocalDateTime;

public class PaymentTransaction {
    private long id;
    private String transactionReference;
    private LocalDateTime transactionDate;
    private TransactionStatus status;
    private long paymentId;

    public PaymentTransaction() {}

    public PaymentTransaction(long id, String transactionReference, LocalDateTime transactionDate, TransactionStatus status, long paymentId) {
        this.id = id;
        this.transactionReference = transactionReference;
        this.transactionDate = transactionDate;
        this.status = status;
        this.paymentId = paymentId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }

    public long getPaymentId() { return paymentId; }
    public void setPaymentId(long paymentId) { this.paymentId = paymentId; }
}
