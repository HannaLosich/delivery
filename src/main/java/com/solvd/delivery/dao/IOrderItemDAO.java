package com.solvd.delivery.dao;

import com.solvd.delivery.models.OrderItem;

import java.util.List;

public interface IOrderItemDAO<T extends OrderItem> extends IBaseDAO<T> {

    List<T> getByOrderId(long orderId);

    List<T> getByProductId(long productId);
}
