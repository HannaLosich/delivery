package com.solvd.delivery.dao;

import com.solvd.delivery.models.User;
import java.util.List;

public interface IUserDAO<T extends User> extends IBaseDAO<T> {
    T getByEmail(String email);
    T getByPhoneNumber(String phoneNumber);
}
