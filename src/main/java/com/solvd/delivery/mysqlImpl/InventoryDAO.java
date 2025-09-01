package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IInventoryDAO;
import com.solvd.delivery.models.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO extends AMySQLDB implements IInventoryDAO<Inventory> {

    @Override
    public void insert(Inventory inventory) {
        String query = "INSERT INTO Inventory (stock_quantity, last_updated, warehouse_id, product_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inventory.getStockQuantity());
            ps.setTimestamp(2, Timestamp.valueOf(inventory.getLastUpdated()));
            ps.setLong(3, inventory.getWarehouseId());
            ps.setLong(4, inventory.getProductId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                inventory.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inventory getById(long id) {
        String query = "SELECT * FROM Inventory WHERE id = ?";
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
    public List<Inventory> getAll() {
        List<Inventory> list = new ArrayList<>();
        String query = "SELECT * FROM Inventory";
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
    public Inventory update(Inventory inventory) {
        String query = "UPDATE Inventory SET stock_quantity = ?, last_updated = ?, warehouse_id = ?, product_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, inventory.getStockQuantity());
            ps.setTimestamp(2, Timestamp.valueOf(inventory.getLastUpdated()));
            ps.setLong(3, inventory.getWarehouseId());
            ps.setLong(4, inventory.getProductId());
            ps.setLong(5, inventory.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    @Override
    public void removeById(long id) {
        String query = "DELETE FROM Inventory WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Inventory mapRow(ResultSet rs) throws SQLException {
        return new Inventory(
                rs.getLong("id"),
                rs.getInt("stock_quantity"),
                rs.getTimestamp("last_updated").toLocalDateTime(),
                rs.getLong("warehouse_id"),
                rs.getLong("product_id")
        );
    }
}
