package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IOrderDAO;
import com.solvd.delivery.models.Order;
import com.solvd.delivery.enums.OrderStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AMySQLDB implements IOrderDAO<Order> {

    private static final String INSERT_SQL =
            "INSERT INTO orders (order_date, status, total_amount, user_id, address_id) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM orders WHERE id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM orders";

    private static final String UPDATE_SQL =
            "UPDATE orders SET order_date=?, status=?, total_amount=?, user_id=?, address_id=? WHERE id=?";

    private static final String DELETE_SQL =
            "DELETE FROM orders WHERE id=?";

    private static final String SELECT_BY_USER_ID_SQL =
            "SELECT * FROM orders WHERE user_id=?";

    private static final String SELECT_BY_ADDRESS_ID_SQL =
            "SELECT * FROM orders WHERE address_id=?";

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
    public Order getById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapOrder(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
            while (rs.next()) {
                orders.add(mapOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
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
    public Order getByUserId(long userId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USER_ID_SQL)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapOrder(rs);
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
                return mapOrder(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        order.setStatus(OrderStatus.fromLabel(rs.getString("status")));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setUserId(rs.getLong("user_id"));
        order.setAddressId(rs.getLong("address_id"));
        return order;
    }

}
