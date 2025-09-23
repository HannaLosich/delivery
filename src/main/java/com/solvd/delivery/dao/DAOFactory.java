package com.solvd.delivery.dao;

import com.solvd.delivery.dao.mysqlImpl.*;

public class DAOFactory {

    public static PaymentDAO getPaymentDAO() {
        return new PaymentDAO();
    }

    public static OrderDAO getOrderDAO() {
        return new OrderDAO();
    }

    public static UserDAO getUserDAO() {
        return new UserDAO();
    }


}
