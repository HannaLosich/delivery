package com.solvd.delivery.services.userRoles;

import com.solvd.delivery.models.User;
import com.solvd.delivery.services.interfaces.IUserService;

import java.sql.SQLException;
import java.util.Optional;

public class AdminUserService implements IUserService {

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void performRoleSpecificAction(User user) {
        System.out.println("Admin privileges granted to " + user.getFirstName());
    }
}
