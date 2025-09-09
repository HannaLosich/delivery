package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IPaymentDAO;
import com.solvd.delivery.exceptions.UnknownPaymentMethodException;
import com.solvd.delivery.models.Payment;
import com.solvd.delivery.enums.PaymentMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PaymentDAO extends GenericDAO<Payment> implements IPaymentDAO<Payment> {

    private static final Logger logger = LogManager.getLogger(PaymentDAO.class);

    private static final String INSERT_SQL =
            "INSERT INTO Payments (amount, payment_method, payment_date, order_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE Payments SET amount=?, payment_method=?, payment_date=?, order_id=? WHERE id=?";
    private static final String SELECT_BY_ORDER_ID_SQL =
            "SELECT * FROM Payments WHERE order_id=?";

    @Override
    protected String getTableName() {
        return "Payments";
    }

    @Override
    protected Payment mapRow(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getLong("id"));
        payment.setAmount(rs.getDouble("amount"));

        String methodLabel = rs.getString("payment_method");
        if (methodLabel != null) {
            try {
                payment.setPaymentMethod(PaymentMethod.fromLabel(methodLabel));
            } catch (UnknownPaymentMethodException e) {
                throw new SQLException("Invalid PaymentMethod value in DB: " + methodLabel, e);
            }
        }

        payment.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
        payment.setOrderId(rs.getLong("order_id"));
        return payment;
    }

    @Override
    public void insert(Payment payment) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, payment.getAmount());
            stmt.setString(2, payment.getPaymentMethod().name());
            stmt.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));
            stmt.setLong(4, payment.getOrderId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    payment.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            logger.error("Error inserting Payment: {}", payment, e);
        }
    }

    @Override
    public Payment update(Payment payment) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setDouble(1, payment.getAmount());
            stmt.setString(2, payment.getPaymentMethod().name());
            stmt.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));
            stmt.setLong(4, payment.getOrderId());
            stmt.setLong(5, payment.getId());

            stmt.executeUpdate();
            return payment;

        } catch (SQLException e) {
            logger.error("Error updating Payment: {}", payment, e);
        }
        return null;
    }

    @Override
    public Payment getByOrderId(long orderId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ORDER_ID_SQL)) {

            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            logger.error("Error fetching Payment for orderId={}", orderId, e);
        }
        return null;
    }
}
