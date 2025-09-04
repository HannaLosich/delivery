package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IOrderPromotionDAO;
import com.solvd.delivery.models.OrderPromotion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderPromotionDAO extends GenericDAO<OrderPromotion> implements IOrderPromotionDAO<OrderPromotion> {

    private static final String INSERT_SQL =
            "INSERT INTO Order_promotions (order_id, promotion_id) VALUES (?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE Order_promotions SET order_id = ?, promotion_id = ? WHERE id = ?";

    @Override
    protected String getTableName() {
        return "Order_promotions";
    }

    @Override
    protected OrderPromotion mapRow(ResultSet rs) throws SQLException {
        OrderPromotion op = new OrderPromotion();
        op.setId(rs.getLong("id"));
        op.setOrderId(rs.getLong("order_id"));
        op.setPromotionId(rs.getLong("promotion_id"));
        return op;
    }

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
}
