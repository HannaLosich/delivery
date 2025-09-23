package com.solvd.delivery.services.factories;

import com.solvd.delivery.enums.UserRole;
import com.solvd.delivery.services.interfaces.IUserService;
import com.solvd.delivery.services.userRoles.AdminUserService;
import com.solvd.delivery.services.userRoles.CourierUserService;
import com.solvd.delivery.services.userRoles.CustomerUserService;

public class UserServiceFactory {

    public static IUserService getService(UserRole role) {
        return switch (role) {
            case ADMIN -> new AdminUserService();
            case CUSTOMER -> new CustomerUserService();
            case COURIER -> new CourierUserService();
        };
    }
}
