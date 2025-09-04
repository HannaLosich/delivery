package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.ICourierDAO;
import com.solvd.delivery.models.Courier;

import java.sql.*;

public class CourierDAO extends GenericDAO<Courier> implements ICourierDAO {

    private static final String INSERT_SQL =
            "INSERT INTO couriers (name, contact_number, email, shipment_id, user_id) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE couriers SET name = ?, contact_number = ?, email = ?, shipment_id = ?, user_id = ? WHERE id = ?";

    @Override
    protected String getTableName() {
        return "couriers";
    }

    @Override
    protected Courier mapRow(ResultSet rs) throws SQLException {
        return new Courier(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("contact_number"),
                rs.getString("email"),
                rs.getLong("shipment_id"),
                rs.getLong("user_id")
        );
    }

    @Override
    public void insert(Courier courier) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, courier.getName());
            ps.setString(2, courier.getContactNumber());
            ps.setString(3, courier.getEmail());
            ps.setLong(4, courier.getShipmentId());
            ps.setLong(5, courier.getUserId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    courier.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Courier update(Courier courier) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, courier.getName());
            ps.setString(2, courier.getContactNumber());
            ps.setString(3, courier.getEmail());
            ps.setLong(4, courier.getShipmentId());
            ps.setLong(5, courier.getUserId());
            ps.setLong(6, courier.getId());

            ps.executeUpdate();
            return courier;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
