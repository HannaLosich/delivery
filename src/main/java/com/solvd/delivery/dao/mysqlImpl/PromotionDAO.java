package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IPromotionDAO;
import com.solvd.delivery.enums.DiscountType;
import com.solvd.delivery.exceptions.UnknownDiscountTypeException;
import com.solvd.delivery.models.Promotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PromotionDAO extends GenericDAO<Promotion> implements IPromotionDAO<Promotion> {

    private static final Logger logger = LogManager.getLogger(PromotionDAO.class);

    private static final String INSERT_SQL =
            "INSERT INTO promotions (code, description, discount_type, discount_value, start_date, end_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE promotions SET code=?, description=?, discount_type=?, discount_value=?, start_date=?, end_date=? WHERE id=?";
    private static final String SELECT_BY_CODE_SQL =
            "SELECT * FROM promotions WHERE code=?";

    @Override
    protected String getTableName() {
        return "promotions";
    }

    @Override
    protected Promotion mapRow(ResultSet rs) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setId(rs.getLong("id"));
        promotion.setCode(rs.getString("code"));
        promotion.setDescription(rs.getString("description"));

        String typeStr = rs.getString("discount_type");
        try {
            promotion.setDiscountType(DiscountType.fromLabel(typeStr));
        } catch (UnknownDiscountTypeException e) {
            throw new SQLException("Invalid discount type in DB: " + typeStr, e);
        }

        promotion.setDiscountValue(rs.getDouble("discount_value"));
        promotion.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
        promotion.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
        return promotion;
    }

    @Override
    public void insert(Promotion promotion) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, promotion.getCode());
            stmt.setString(2, promotion.getDescription());
            stmt.setInt(3, promotion.getDiscountType().getId());
            stmt.setDouble(4, promotion.getDiscountValue());
            stmt.setTimestamp(5, Timestamp.valueOf(promotion.getStartDate()));
            stmt.setTimestamp(6, Timestamp.valueOf(promotion.getEndDate()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    promotion.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            logger.error("Error inserting Promotion: {}", promotion, e);
        }

    }

    @Override
    public Promotion update(Promotion promotion) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, promotion.getCode());
            stmt.setString(2, promotion.getDescription());
            stmt.setInt(3, promotion.getDiscountType().getId());
            stmt.setDouble(4, promotion.getDiscountValue());
            stmt.setTimestamp(5, Timestamp.valueOf(promotion.getStartDate()));
            stmt.setTimestamp(6, Timestamp.valueOf(promotion.getEndDate()));
            stmt.setLong(7, promotion.getId());

            stmt.executeUpdate();
            return promotion;

        } catch (SQLException e) {
            logger.error("Error updating Promotion: {}", promotion, e);
        }

        return null;
    }

    @Override
    public Promotion getByCode(String code) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CODE_SQL)) {

            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            logger.error("Error fetching Promotion by code={}", code, e);
        }

        return null;
    }
}
