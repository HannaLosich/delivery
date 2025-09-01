package com.solvd.delivery.dao;

import com.solvd.delivery.models.Address;

public interface IAddressDAO<T extends Address> extends IBaseDAO<T> {
    T getByUserId(long userId);
}
