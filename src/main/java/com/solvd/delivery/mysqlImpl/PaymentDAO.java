package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IPaymentDAO;
import com.solvd.delivery.models.Payment;
import com.solvd.delivery.enums.PaymentMethod;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO extends AMySQLDB implements IPaymentDAO<Payment> {

    private static final String INSERT_SQL =
            "INSERT INTO Payments (amount, payment_method, payment_date, order_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM Payments WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT * FROM Payments";
    private static final String UPDATE_SQL =
            "UPDATE Payments SET amount=?, payment_method=?, payment_date=?, order_id=? WHERE id=?";
    private static final String DELETE_SQL =
            "DELETE FROM Payments WHERE id=?";
    private static final String SELECT_BY_ORDER_ID_SQL =
            "SELECT * FROM Payments WHERE order_id=?";

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
            e.printStackTrace();
        }
    }

    @Override
    public Payment getById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPayment(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
            while (rs.next()) {
                payments.add(mapPayment(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Payment getByOrderId(long orderId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ORDER_ID_SQL)) {

            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPayment(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Payment mapPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getLong("id"));
        payment.setAmount(rs.getDouble("amount"));

        // Use label from DB and convert to enum
        String methodLabel = rs.getString("payment_method");
        if (methodLabel != null) {
            payment.setPaymentMethod(PaymentMethod.fromLabel(methodLabel));
        }

        payment.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
        payment.setOrderId(rs.getLong("order_id"));
        return payment;
    }

}
