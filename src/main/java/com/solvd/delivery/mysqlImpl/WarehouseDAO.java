package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IWarehouseDAO;
import com.solvd.delivery.models.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO extends AMySQLDB implements IWarehouseDAO<Warehouse> {

    @Override
    public void insert(Warehouse warehouse) {
        String query = "INSERT INTO Warehouses (name, location) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, warehouse.getName());
            ps.setString(2, warehouse.getLocation());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                warehouse.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Warehouse getById(long id) {
        String query = "SELECT * FROM Warehouses WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Warehouse> getAll() {
        List<Warehouse> warehouses = new ArrayList<>();
        String query = "SELECT * FROM Warehouses";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                warehouses.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouses;
    }

    @Override
    public Warehouse update(Warehouse warehouse) {
        String query = "UPDATE Warehouses SET name = ?, location = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, warehouse.getName());
            ps.setString(2, warehouse.getLocation());
            ps.setLong(3, warehouse.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse; // return the updated object
    }


    @Override
    public void removeById(long id) {
        String query = "DELETE FROM Warehouses WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Warehouse mapRow(ResultSet rs) throws SQLException {
        return new Warehouse(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("location")
        );
    }
}
