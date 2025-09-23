package com.solvd.delivery.dao;

import com.solvd.delivery.models.Order;
import java.util.List;

public interface IOrderDAO<T extends Order> extends IBaseDAO<T> {

    List<T> getByUserId(long userId);
    T getByAddressId(long addressId);
}
