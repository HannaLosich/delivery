package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IOrderDAO;
import com.solvd.delivery.exceptions.UnknownOrderStatusException;
import com.solvd.delivery.models.Order;
import com.solvd.delivery.enums.OrderStatus;

import java.sql.*;

public class OrderDAO extends GenericDAO<Order> implements IOrderDAO<Order> {

    private static final String INSERT_SQL =
            "INSERT INTO orders (order_date, status, total_amount, user_id, address_id) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE orders SET order_date=?, status=?, total_amount=?, user_id=?, address_id=? WHERE id=?";

    private static final String SELECT_BY_USER_ID_SQL =
            "SELECT * FROM orders WHERE user_id=?";

    private static final String SELECT_BY_ADDRESS_ID_SQL =
            "SELECT * FROM orders WHERE address_id=?";

    @Override
    protected String getTableName() {
        return "orders";
    }

    @Override
    protected Order mapRow(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());

        String statusStr = rs.getString("status");
        try {
            order.setStatus(OrderStatus.fromLabel(statusStr));
        } catch (UnknownOrderStatusException e) {
            throw new SQLException("Invalid order status in DB: " + statusStr, e);
        }

        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setUserId(rs.getLong("user_id"));
        order.setAddressId(rs.getLong("address_id"));
        return order;
    }

    @Override
    public void insert(Order order) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            stmt.setString(2, order.getStatus().name());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setLong(4, order.getUserId());
            stmt.setLong(5, order.getAddressId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    order.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order update(Order order) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            stmt.setString(2, order.getStatus().name());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setLong(4, order.getUserId());
            stmt.setLong(5, order.getAddressId());
            stmt.setLong(6, order.getId());

            stmt.executeUpdate();
            return order;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order getByUserId(long userId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USER_ID_SQL)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order getByAddressId(long addressId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ADDRESS_ID_SQL)) {

            stmt.setLong(1, addressId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
