package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IShipmentDAO;
import com.solvd.delivery.exceptions.UnknownShipmentStatusException;
import com.solvd.delivery.models.Shipment;
import com.solvd.delivery.enums.ShipmentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDAO extends GenericDAO<Shipment> implements IShipmentDAO<Shipment> {

    private static final String INSERT_SQL =
            "INSERT INTO shipments (shipment_date, delivery_date, status, order_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE shipments SET shipment_date=?, delivery_date=?, status=?, order_id=? WHERE id=?";
    private static final String SELECT_BY_ORDER_ID_SQL =
            "SELECT * FROM shipments WHERE order_id=?";

    @Override
    protected String getTableName() {
        return "shipments";
    }

    @Override
    protected Shipment mapRow(ResultSet rs) throws SQLException {
        Shipment shipment = new Shipment();
        shipment.setId(rs.getLong("id"));
        shipment.setShipmentDate(rs.getTimestamp("shipment_date").toLocalDateTime());
        shipment.setDeliveryDate(rs.getTimestamp("delivery_date").toLocalDateTime());

        String statusLabel = rs.getString("status");
        if (statusLabel != null) {
            try {
                shipment.setStatus(ShipmentStatus.fromLabel(statusLabel));
            } catch (UnknownShipmentStatusException e) {
                throw new SQLException("Invalid ShipmentStatus in DB: " + statusLabel, e);
            }
        }

        shipment.setOrderId(rs.getLong("order_id"));
        return shipment;
    }

    @Override
    public void insert(Shipment shipment) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(shipment.getShipmentDate()));
            stmt.setTimestamp(2, Timestamp.valueOf(shipment.getDeliveryDate()));
            stmt.setString(3, shipment.getStatus().name());
            stmt.setLong(4, shipment.getOrderId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    shipment.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Shipment update(Shipment shipment) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setTimestamp(1, Timestamp.valueOf(shipment.getShipmentDate()));
            stmt.setTimestamp(2, Timestamp.valueOf(shipment.getDeliveryDate()));
            stmt.setString(3, shipment.getStatus().name());
            stmt.setLong(4, shipment.getOrderId());
            stmt.setLong(5, shipment.getId());
            stmt.executeUpdate();
            return shipment;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Shipment> getByOrderId(long orderId) {
        List<Shipment> shipments = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ORDER_ID_SQL)) {

            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) shipments.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shipments;
    }
}
