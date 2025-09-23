package com.solvd.delivery.services.interfaces;

import com.solvd.delivery.enums.PaymentMethod;
import com.solvd.delivery.models.Payment;

public interface IPaymentService {
    void pay(PaymentMethod method, Payment payment);
    Payment getByOrderId(long orderId);
}
