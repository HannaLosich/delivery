package com.solvd.delivery.services.impl;

import com.solvd.delivery.dao.DAOFactory;
import com.solvd.delivery.dao.mysqlImpl.PaymentDAO;
import com.solvd.delivery.dao.mysqlImpl.OrderDAO;
import com.solvd.delivery.dao.mysqlImpl.UserDAO;
import com.solvd.delivery.enums.PaymentMethod;
import com.solvd.delivery.models.Payment;
import com.solvd.delivery.models.Order;
import com.solvd.delivery.models.User;
import com.solvd.delivery.services.interfaces.IPaymentService;

public class PaymentService implements IPaymentService {

    private final PaymentDAO paymentDAO = DAOFactory.getPaymentDAO();
    private final OrderDAO orderDAO = DAOFactory.getOrderDAO();
    private final UserDAO userDAO = DAOFactory.getUserDAO();

    @Override
    public void pay(PaymentMethod method, Payment payment) {
        payment.setPaymentMethod(method);
        paymentDAO.insert(payment);

        Order order = orderDAO.getById(payment.getOrderId());
        User user = userDAO.getById(order.getUserId());

        System.out.println("Payment processed for user " + user.getFirstName() + " with method " + method.getLabel());
    }

    @Override
    public Payment getByOrderId(long orderId) {
        return paymentDAO.getByOrderId(orderId);
    }
}
