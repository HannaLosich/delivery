package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.ITrackingDAO;
import com.solvd.delivery.models.Tracking;
import com.solvd.delivery.enums.TrackingStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrackingDAO extends AMySQLDB implements ITrackingDAO<Tracking> {

    private static final String INSERT_SQL =
            "INSERT INTO tracking (tracking_number, status, last_update, shipment_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM tracking WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT * FROM tracking";
    private static final String UPDATE_SQL =
            "UPDATE tracking SET tracking_number=?, status=?, last_update=?, shipment_id=? WHERE id=?";
    private static final String DELETE_SQL =
            "DELETE FROM tracking WHERE id=?";
    private static final String SELECT_BY_SHIPMENT_ID_SQL =
            "SELECT * FROM tracking WHERE shipment_id=?";

    @Override
    public void insert(Tracking tracking) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tracking.getTrackingNumber());
            stmt.setString(2, tracking.getStatus().name());
            stmt.setTimestamp(3, Timestamp.valueOf(tracking.getLastUpdate()));
            stmt.setLong(4, tracking.getShipmentId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tracking.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Tracking getById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapTracking(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tracking> getAll() {
        List<Tracking> trackings = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
            while (rs.next()) {
                trackings.add(mapTracking(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trackings;
    }

    @Override
    public Tracking update(Tracking tracking) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, tracking.getTrackingNumber());
            stmt.setString(2, tracking.getStatus().name());
            stmt.setTimestamp(3, Timestamp.valueOf(tracking.getLastUpdate()));
            stmt.setLong(4, tracking.getShipmentId());
            stmt.setLong(5, tracking.getId());
            stmt.executeUpdate();
            return tracking;

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
    public List<Tracking> getByShipmentId(long shipmentId) {
        List<Tracking> trackings = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_SHIPMENT_ID_SQL)) {

            stmt.setLong(1, shipmentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trackings.add(mapTracking(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trackings;
    }

    private Tracking mapTracking(ResultSet rs) throws SQLException {
        Tracking tracking = new Tracking();
        tracking.setId(rs.getLong("id"));
        tracking.setTrackingNumber(rs.getString("tracking_number"));
        tracking.setStatus(TrackingStatus.valueOf(rs.getString("status").toUpperCase()));
        tracking.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        tracking.setShipmentId(rs.getLong("shipment_id"));
        return tracking;
    }
}
