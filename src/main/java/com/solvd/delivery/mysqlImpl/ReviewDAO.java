package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IReviewDAO;
import com.solvd.delivery.models.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO extends AMySQLDB implements IReviewDAO<Review> {

    @Override
    public void insert(Review review) {
        String query = "INSERT INTO Reviews (rating, comment, created_at, user_id, shipment_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setTimestamp(3, Timestamp.valueOf(review.getCreatedAt()));
            ps.setLong(4, review.getUserId());
            ps.setLong(5, review.getShipmentId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                review.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Review getById(long id) {
        String query = "SELECT * FROM Reviews WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Review> getAll() {
        List<Review> list = new ArrayList<>();
        String query = "SELECT * FROM Reviews";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Review update(Review review) {
        String query = "UPDATE Reviews SET rating = ?, comment = ?, created_at = ?, user_id = ?, shipment_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setTimestamp(3, Timestamp.valueOf(review.getCreatedAt()));
            ps.setLong(4, review.getUserId());
            ps.setLong(5, review.getShipmentId());
            ps.setLong(6, review.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }

    @Override
    public void removeById(long id) {
        String query = "DELETE FROM Reviews WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Review mapRow(ResultSet rs) throws SQLException {
        return new Review(
                rs.getLong("id"),
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getLong("user_id"),
                rs.getLong("shipment_id")
        );
    }
}
