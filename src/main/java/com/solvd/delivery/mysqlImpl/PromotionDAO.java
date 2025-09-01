package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IPromotionDAO;
import com.solvd.delivery.enums.DiscountType;
import com.solvd.delivery.models.Promotion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO extends AMySQLDB implements IPromotionDAO<Promotion> {

    private static final String INSERT_SQL =
            "INSERT INTO promotions (code, description, discount_type, discount_value, start_date, end_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM promotions WHERE id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM promotions";

    private static final String UPDATE_SQL =
            "UPDATE promotions SET code=?, description=?, discount_type=?, discount_value=?, start_date=?, end_date=? WHERE id=?";

    private static final String DELETE_SQL =
            "DELETE FROM promotions WHERE id=?";

    private static final String SELECT_BY_CODE_SQL =
            "SELECT * FROM promotions WHERE code=?";

    @Override
    public void insert(Promotion promotion) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, promotion.getCode());
            stmt.setString(2, promotion.getDescription());
            stmt.setInt(3, promotion.getDiscountType().getId());  // store enum as ID
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
            e.printStackTrace();
        }
    }

    @Override
    public Promotion getById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPromotion(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Promotion> getAll() {
        List<Promotion> promotions = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
            while (rs.next()) {
                promotions.add(mapPromotion(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
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
    public Promotion getByCode(String code) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CODE_SQL)) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPromotion(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Promotion mapPromotion(ResultSet rs) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setId(rs.getLong("id"));
        promotion.setCode(rs.getString("code"));
        promotion.setDescription(rs.getString("description"));

        String typeStr = rs.getString("discount_type");
        promotion.setDiscountType(DiscountType.fromLabel(typeStr));


        promotion.setDiscountValue(rs.getDouble("discount_value"));
        promotion.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
        promotion.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());

        return promotion;
    }

}
