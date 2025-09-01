package com.solvd.delivery.dao;

import com.solvd.delivery.models.Payment;

public interface IPaymentDAO<T extends Payment> extends IBaseDAO<T> {
    T getByOrderId(long orderId);
}
