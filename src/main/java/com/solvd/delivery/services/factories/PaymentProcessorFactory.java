package com.solvd.delivery.services.factories;

import com.solvd.delivery.enums.PaymentMethod;
import com.solvd.delivery.services.interfaces.IPaymentProcessor;
import com.solvd.delivery.services.payment.BankTransferProcessor;
import com.solvd.delivery.services.payment.CashOnDeliveryProcessor;
import com.solvd.delivery.services.payment.CreditCardProcessor;
import com.solvd.delivery.services.payment.PayPalProcessor;

public class PaymentProcessorFactory {

    public static IPaymentProcessor getProcessor(PaymentMethod method) {
        return switch (method) {
            case CREDIT_CARD -> new CreditCardProcessor();
            case PAYPAL -> new PayPalProcessor();
            case BANK_TRANSFER -> new BankTransferProcessor();
            case CASH_ON_DELIVERY -> new CashOnDeliveryProcessor();
        };
    }
}
