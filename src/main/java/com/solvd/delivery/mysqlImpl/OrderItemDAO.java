package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IOrderItemDAO;
import com.solvd.delivery.models.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO extends AMySQLDB implements IOrderItemDAO<OrderItem> {

    private static final String INSERT_SQL =
            "INSERT INTO order_items (quantity, unit_price, order_id, product_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM order_items WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT * FROM order_items";
    private static final String UPDATE_SQL =
            "UPDATE order_items SET quantity=?, unit_price=?, order_id=?, product_id=? WHERE id=?";
    private static final String DELETE_SQL =
            "DELETE FROM order_items WHERE id=?";
    private static final String SELECT_BY_ORDER_ID_SQL =
            "SELECT * FROM order_items WHERE order_id=?";
    private static final String SELECT_BY_PRODUCT_ID_SQL =
            "SELECT * FROM order_items WHERE product_id=?";

    @Override
    public void insert(OrderItem item) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, item.getQuantity());
            stmt.setDouble(2, item.getUnitPrice());
            stmt.setLong(3, item.getOrderId());
            stmt.setLong(4, item.getProductId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderItem getById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapOrderItem(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderItem> getAll() {
        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
            while (rs.next()) {
                items.add(mapOrderItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public OrderItem update(OrderItem item) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setInt(1, item.getQuantity());
            stmt.setDouble(2, item.getUnitPrice());
            stmt.setLong(3, item.getOrderId());
            stmt.setLong(4, item.getProductId());
            stmt.setLong(5, item.getId());
            stmt.executeUpdate();
            return item;

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
    public List<OrderItem> getByOrderId(long orderId) {
        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ORDER_ID_SQL)) {

            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(mapOrderItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<OrderItem> getByProductId(long productId) {
        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PRODUCT_ID_SQL)) {

            stmt.setLong(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(mapOrderItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    private OrderItem mapOrderItem(ResultSet rs) throws SQLException {
        return new OrderItem(
                rs.getLong("id"),
                rs.getInt("quantity"),
                rs.getDouble("unit_price"),
                rs.getLong("order_id"),
                rs.getLong("product_id")
        );
    }
}
