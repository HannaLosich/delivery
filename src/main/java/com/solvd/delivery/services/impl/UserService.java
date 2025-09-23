package com.solvd.delivery.services.impl;

import com.solvd.delivery.dao.mysqlImpl.UserDAO;
import com.solvd.delivery.dao.mysqlImpl.OrderDAO;
import com.solvd.delivery.models.User;
import com.solvd.delivery.models.Order;
import com.solvd.delivery.services.interfaces.IUserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {

    private final UserDAO userDAO = new UserDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        User user = userDAO.getById(id);
        if (user == null) {
            return Optional.empty();
        }

        // Enrich user with their orders
        List<Order> orders = orderDAO.getByUserId(id);
        user.setOrders(orders);

        return Optional.of(user);
    }

    @Override
    public void performRoleSpecificAction(User user) {
        // Example implementation: perform an action based on the user's role
        if (user.getRole() == null) {
            System.out.println("User " + user.getName() + " has no role assigned.");
            return;
        }

        switch (user.getRole()) {
            case CUSTOMER:
                System.out.println("Performing customer-specific action for " + user.getName());
                break;
            case ADMIN:
                System.out.println("Performing admin-specific action for " + user.getName());
                break;
            case COURIER:
                System.out.println("Performing manager-specific action for " + user.getName());
                break;
            default:
                System.out.println("Unknown role for user " + user.getName());
        }
    }
}
