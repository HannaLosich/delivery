package com.solvd.delivery.services.interfaces;

import com.solvd.delivery.models.Payment;

public interface IPaymentProcessor {
    void processPayment(Payment payment);
}
