package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IPaymentTransactionDAO;
import com.solvd.delivery.models.PaymentTransaction;
import com.solvd.delivery.enums.TransactionStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentTransactionDAO extends AMySQLDB implements IPaymentTransactionDAO<PaymentTransaction> {

    private static final String INSERT_SQL =
            "INSERT INTO payment_transactions (transaction_reference, transaction_date, status, payment_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM payment_transactions WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT * FROM payment_transactions";
    private static final String UPDATE_SQL =
            "UPDATE payment_transactions SET transaction_reference=?, transaction_date=?, status=?, payment_id=? WHERE id=?";
    private static final String DELETE_SQL =
            "DELETE FROM payment_transactions WHERE id=?";
    private static final String SELECT_BY_PAYMENT_ID_SQL =
            "SELECT * FROM payment_transactions WHERE payment_id=?";

    @Override
    public void insert(PaymentTransaction transaction) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, transaction.getTransactionReference());
            stmt.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
            stmt.setString(3, transaction.getStatus().name());
            stmt.setLong(4, transaction.getPaymentId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    transaction.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PaymentTransaction getById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapTransaction(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PaymentTransaction> getAll() {
        List<PaymentTransaction> transactions = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public PaymentTransaction update(PaymentTransaction transaction) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, transaction.getTransactionReference());
            stmt.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
            stmt.setString(3, transaction.getStatus().name());
            stmt.setLong(4, transaction.getPaymentId());
            stmt.setLong(5, transaction.getId());
            stmt.executeUpdate();
            return transaction;

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
    public List<PaymentTransaction> getByPaymentId(long paymentId) {
        List<PaymentTransaction> transactions = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PAYMENT_ID_SQL)) {

            stmt.setLong(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private PaymentTransaction mapTransaction(ResultSet rs) throws SQLException {
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setId(rs.getLong("id"));
        transaction.setTransactionReference(rs.getString("transaction_reference"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());

        String statusStr = rs.getString("status");
        if (statusStr != null) {
            transaction.setStatus(TransactionStatus.fromLabel(statusStr));
        }

        transaction.setPaymentId(rs.getLong("payment_id"));
        return transaction;
    }
}
