package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IOrderPromotionDAO;
import com.solvd.delivery.models.OrderPromotion;
import com.solvd.delivery.mysqlImpl.AMySQLDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderPromotionDAO extends AMySQLDB implements IOrderPromotionDAO<OrderPromotion> {

    private static final String INSERT_SQL =
            "INSERT INTO Order_promotions (order_id, promotion_id) VALUES (?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT id, order_id, promotion_id FROM Order_promotions WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT id, order_id, promotion_id FROM Order_promotions";
    private static final String UPDATE_SQL =
            "UPDATE Order_promotions SET order_id = ?, promotion_id = ? WHERE id = ?";
    private static final String DELETE_SQL =
            "DELETE FROM Order_promotions WHERE id = ?";

    @Override
    public void insert(OrderPromotion op) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, op.getOrderId());
            stmt.setLong(2, op.getPromotionId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    op.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderPromotion getById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapOrderPromotion(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderPromotion> getAll() {
        List<OrderPromotion> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapOrderPromotion(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(OrderPromotion op) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setLong(1, op.getOrderId());
            stmt.setLong(2, op.getPromotionId());
            stmt.setLong(3, op.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private OrderPromotion mapOrderPromotion(ResultSet rs) throws SQLException {
        OrderPromotion op = new OrderPromotion();
        op.setId(rs.getLong("id"));
        op.setOrderId(rs.getLong("order_id"));
        op.setPromotionId(rs.getLong("promotion_id"));
        return op;
    }
}
