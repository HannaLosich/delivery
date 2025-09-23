package com.solvd.delivery.services.payment;

import com.solvd.delivery.models.Payment;
import com.solvd.delivery.services.interfaces.IPaymentProcessor;

public class BankTransferProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(Payment payment) {
        System.out.println("Processing Bank Transfer payment for amount: " + payment.getAmount());
    }
}
