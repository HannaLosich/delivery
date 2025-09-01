package com.solvd.delivery.dao;

import com.solvd.delivery.models.Order;

public interface IOrderDAO<T extends Order> extends IBaseDAO<T> {

    T getByUserId(long userId);
    T getByAddressId(long addressId);
}
