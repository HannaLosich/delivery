package com.solvd.delivery.dao;

import com.solvd.delivery.models.PaymentTransaction;
import java.util.List;

public interface IPaymentTransactionDAO<T extends PaymentTransaction> extends IBaseDAO<T> {

    List<T> getByPaymentId(long paymentId);
}
