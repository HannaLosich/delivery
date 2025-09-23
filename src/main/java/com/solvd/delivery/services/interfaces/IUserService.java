package com.solvd.delivery.services.interfaces;

import com.solvd.delivery.models.User;
import java.sql.SQLException;
import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id) throws SQLException;
    void performRoleSpecificAction(User user);
}
