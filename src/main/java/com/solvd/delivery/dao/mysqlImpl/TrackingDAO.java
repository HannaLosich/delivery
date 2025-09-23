package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.ITrackingDAO;
import com.solvd.delivery.enums.TrackingStatus;
import com.solvd.delivery.exceptions.UnknownTrackingStatusException;
import com.solvd.delivery.models.Tracking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackingDAO extends GenericDAO<Tracking> implements ITrackingDAO<Tracking> {

    private static final Logger logger = LogManager.getLogger(TrackingDAO.class);

    private static final String INSERT_SQL =
            "INSERT INTO tracking (tracking_number, status, last_update, shipment_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE tracking SET tracking_number=?, status=?, last_update=?, shipment_id=? WHERE id=?";
    private static final String DELETE_SQL =
            "DELETE FROM tracking WHERE id=?";
    private static final String SELECT_BY_SHIPMENT_ID_SQL =
            "SELECT * FROM tracking WHERE shipment_id=?";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM tracking WHERE id=?";
    private static final String SELECT_ALL_SQL =
            "SELECT * FROM tracking";

    @Override
    protected String getTableName() {
        return "tracking";
    }

    @Override
    protected Tracking mapRow(ResultSet rs) throws SQLException {
        Tracking tracking = new Tracking();
        tracking.setId(rs.getLong("id"));
        tracking.setTrackingNumber(rs.getString("tracking_number"));

        String statusStr = rs.getString("status");
        try {
            tracking.setStatus(TrackingStatus.fromDbValue(statusStr)); // map DB string to enum
        } catch (UnknownTrackingStatusException e) {
            throw new SQLException("Invalid TrackingStatus in DB: " + statusStr, e);
        }

        Timestamp ts = rs.getTimestamp("last_update");
        if (ts != null) {
            tracking.setLastUpdate(ts.toLocalDateTime());
        }

        tracking.setShipmentId(rs.getLong("shipment_id"));
        return tracking;
    }

    @Override
    public void insert(Tracking tracking) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tracking.getTrackingNumber());
            stmt.setString(2, tracking.getStatus().getDbValue()); // store as DB value
            stmt.setTimestamp(3, Timestamp.valueOf(tracking.getLastUpdate()));
            stmt.setLong(4, tracking.getShipmentId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tracking.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            logger.error("Error inserting Tracking: {}", tracking, e);
        }
    }

    @Override
    public Tracking update(Tracking tracking) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, tracking.getTrackingNumber());
            stmt.setString(2, tracking.getStatus().getDbValue()); // store as DB value
            stmt.setTimestamp(3, Timestamp.valueOf(tracking.getLastUpdate()));
            stmt.setLong(4, tracking.getShipmentId());
            stmt.setLong(5, tracking.getId());

            stmt.executeUpdate();
            return tracking;

        } catch (SQLException e) {
            logger.error("Error updating Tracking: {}", tracking, e);
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
            logger.error("Error deleting Tracking with id={}", id, e);
        }
    }

    @Override
    public Tracking getById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            logger.error("Error retrieving Tracking by id={}", id, e);
        }
        return null;
    }

    @Override
    public List<Tracking> getAll() {
        List<Tracking> trackings = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                trackings.add(mapRow(rs));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving all trackings", e);
        }
        return trackings;
    }

    @Override
    public List<Tracking> getByShipmentId(long shipmentId) {
        List<Tracking> trackings = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_SHIPMENT_ID_SQL)) {

            stmt.setLong(1, shipmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    trackings.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error retrieving trackings for shipmentId={}", shipmentId, e);
        }
        return trackings;
    }

    public Tracking getByShipmentIdSingle(long shipmentId) {
        List<Tracking> trackings = getByShipmentId(shipmentId);
        return trackings.isEmpty() ? null : trackings.get(0);
    }

}
