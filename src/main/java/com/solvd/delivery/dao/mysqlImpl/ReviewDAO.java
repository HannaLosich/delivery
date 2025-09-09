package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IReviewDAO;
import com.solvd.delivery.models.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


public class ReviewDAO extends GenericDAO<Review> implements IReviewDAO<Review> {

    private static final Logger logger = LogManager.getLogger(ReviewDAO.class);
    private static final String INSERT_SQL =
            "INSERT INTO Reviews (rating, comment, created_at, user_id, shipment_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE Reviews SET rating = ?, comment = ?, created_at = ?, user_id = ?, shipment_id = ? WHERE id=?";

    @Override
    protected String getTableName() {
        return "Reviews";
    }

    @Override
    protected Review mapRow(ResultSet rs) throws SQLException {
        return new Review(
                rs.getLong("id"),
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getLong("user_id"),
                rs.getLong("shipment_id")
        );
    }

    @Override
    public void insert(Review review) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setTimestamp(3, Timestamp.valueOf(review.getCreatedAt()));
            ps.setLong(4, review.getUserId());
            ps.setLong(5, review.getShipmentId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    review.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            logger.error("Error inserting Review: {}", review, e);
        }

    }

    @Override
    public Review update(Review review) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setTimestamp(3, Timestamp.valueOf(review.getCreatedAt()));
            ps.setLong(4, review.getUserId());
            ps.setLong(5, review.getShipmentId());
            ps.setLong(6, review.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error updating Review: {}", review, e);
        }

        return review;
    }
}
