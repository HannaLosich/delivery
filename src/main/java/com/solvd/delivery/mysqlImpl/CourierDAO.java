package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.ICourierDAO;
import com.solvd.delivery.models.Courier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourierDAO extends AMySQLDB implements ICourierDAO {

    @Override
    public void insert(Courier courier) {
        String query = "INSERT INTO Couriers (name, contact_number, email, shipment_id, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, courier.getName());
            ps.setString(2, courier.getContactNumber());
            ps.setString(3, courier.getEmail());
            ps.setLong(4, courier.getShipmentId());
            ps.setLong(5, courier.getUserId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                courier.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Courier getById(long id) {
        String query = "SELECT * FROM Couriers WHERE id = ?";
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
    public List<Courier> getAll() {
        List<Courier> list = new ArrayList<>();
        String query = "SELECT * FROM Couriers";
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
    public Courier update(Courier courier) {
        String query = "UPDATE Couriers SET name = ?, contact_number = ?, email = ?, shipment_id = ?, user_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, courier.getName());
            ps.setString(2, courier.getContactNumber());
            ps.setString(3, courier.getEmail());
            ps.setLong(4, courier.getShipmentId());
            ps.setLong(5, courier.getUserId());
            ps.setLong(6, courier.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courier;
    }

    @Override
    public void removeById(long id) {
        String query = "DELETE FROM Couriers WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Courier mapRow(ResultSet rs) throws SQLException {
        return new Courier(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("contact_number"),
                rs.getString("email"),
                rs.getLong("shipment_id"),
                rs.getLong("user_id")
        );
    }
}
