package com.solvd.delivery.dao.mybatis;

import com.solvd.delivery.dao.IUserDAO;
import com.solvd.delivery.models.User;

import java.util.List;

public interface UserMapper extends IUserDAO<User> {
    void insert(User user);
    User update(User user);
    User getByEmail(String email);
    User getByPhoneNumber(String phoneNumber);
    User getById(long id);
    List<User> getAll();
    void delete(long id);
}
