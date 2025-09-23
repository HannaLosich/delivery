package com.solvd.delivery.dao.mybatis;

import com.solvd.delivery.models.Payment;
import java.util.List;

public interface PaymentMapper {

    void insert(Payment payment);

    int update(Payment payment);

    Payment getById(long id);

    Payment getByOrderId(long orderId);

    List<Payment> getAll();

    void delete(long id);
}
