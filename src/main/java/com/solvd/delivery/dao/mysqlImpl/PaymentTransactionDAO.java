package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IPaymentTransactionDAO;
import com.solvd.delivery.exceptions.UnknownTransactionStatusException;
import com.solvd.delivery.models.PaymentTransaction;
import com.solvd.delivery.enums.TransactionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentTransactionDAO extends GenericDAO<PaymentTransaction> implements IPaymentTransactionDAO<PaymentTransaction> {
    private static final Logger logger = LogManager.getLogger(PaymentTransactionDAO.class);

    private static final String INSERT_SQL =
            "INSERT INTO payment_transactions (transaction_reference, transaction_date, status, payment_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE payment_transactions SET transaction_reference=?, transaction_date=?, status=?, payment_id=? WHERE id=?";
    private static final String SELECT_BY_PAYMENT_ID_SQL =
            "SELECT * FROM payment_transactions WHERE payment_id=?";

    @Override
    protected String getTableName() {
        return "payment_transactions";
    }

    @Override
    protected PaymentTransaction mapRow(ResultSet rs) throws SQLException {
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setId(rs.getLong("id"));
        transaction.setTransactionReference(rs.getString("transaction_reference"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());

        String statusStr = rs.getString("status");
        if (statusStr != null) {
            try {
                transaction.setStatus(TransactionStatus.fromLabel(statusStr));
            } catch (UnknownTransactionStatusException e) {
                throw new SQLException("Invalid TransactionStatus in DB: " + statusStr, e);
            }
        }

        transaction.setPaymentId(rs.getLong("payment_id"));
        return transaction;
    }

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
            logger.error("Error inserting PaymentTransaction: {}", transaction, e);
        }

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
            logger.error("Error updating PaymentTransaction: {}", transaction, e);
        }

        return null;
    }

    @Override
    public List<PaymentTransaction> getByPaymentId(long paymentId) {
        List<PaymentTransaction> transactions = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PAYMENT_ID_SQL)) {

            stmt.setLong(1, paymentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error fetching PaymentTransactions for paymentId={}", paymentId, e);
        }

        return transactions;
    }
}
