package com.solvd.delivery.services.payment;

import com.solvd.delivery.models.Payment;
import com.solvd.delivery.services.interfaces.IPaymentProcessor;

public class CreditCardProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(Payment payment) {
        System.out.println("Processing credit card payment for amount: " + payment.getAmount());
    }
}
