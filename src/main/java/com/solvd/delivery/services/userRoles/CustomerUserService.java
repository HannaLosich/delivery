package com.solvd.delivery.services.userRoles;

import com.solvd.delivery.models.User;
import com.solvd.delivery.services.interfaces.IUserService;

import java.sql.SQLException;
import java.util.Optional;

public class CustomerUserService implements IUserService {

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void performRoleSpecificAction(User user) {
        System.out.println("Customer access for " + user.getFirstName());
    }
}
