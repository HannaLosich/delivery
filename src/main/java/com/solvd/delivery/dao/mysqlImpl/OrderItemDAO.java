package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IOrderItemDAO;
import com.solvd.delivery.models.OrderItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO extends GenericDAO<OrderItem> implements IOrderItemDAO<OrderItem> {

    private static final Logger logger = LogManager.getLogger(OrderItemDAO.class);

    private static final String INSERT_SQL =
            "INSERT INTO order_items (quantity, unit_price, order_id, product_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE order_items SET quantity=?, unit_price=?, order_id=?, product_id=? WHERE id=?";
    private static final String SELECT_BY_ORDER_ID_SQL =
            "SELECT * FROM order_items WHERE order_id=?";
    private static final String SELECT_BY_PRODUCT_ID_SQL =
            "SELECT * FROM order_items WHERE product_id=?";

    @Override
    protected String getTableName() {
        return "order_items";
    }

    @Override
    protected OrderItem mapRow(ResultSet rs) throws SQLException {
        return new OrderItem(
                rs.getLong("id"),
                rs.getInt("quantity"),
                rs.getDouble("unit_price"),
                rs.getLong("order_id"),
                rs.getLong("product_id")
        );
    }

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
            logger.error("Error inserting OrderItem: {}", item, e);
        }
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
            logger.error("Error updating OrderItem: {}", item, e);
        }
        return null;
    }

    @Override
    public List<OrderItem> getByOrderId(long orderId) {
        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ORDER_ID_SQL)) {

            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(mapRow(rs));
            }

        } catch (SQLException e) {
            logger.error("Error fetching OrderItems by orderId={}", orderId, e);
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
                items.add(mapRow(rs));
            }

        } catch (SQLException e) {
            logger.error("Error fetching OrderItems by productId={}", productId, e);
        }
        return items;
    }
}
