package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IInventoryDAO;
import com.solvd.delivery.models.Inventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class InventoryDAO extends GenericDAO<Inventory> implements IInventoryDAO<Inventory> {

    private static final Logger logger = LogManager.getLogger(InventoryDAO.class);

    private static final String INSERT_SQL =
            "INSERT INTO inventory (stock_quantity, last_updated, warehouse_id, product_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE inventory SET stock_quantity = ?, last_updated = ?, warehouse_id = ?, product_id = ? WHERE id = ?";

    @Override
    protected String getTableName() {
        return "inventory";
    }

    @Override
    protected Inventory mapRow(ResultSet rs) throws SQLException {
        return new Inventory(
                rs.getLong("id"),
                rs.getInt("stock_quantity"),
                rs.getTimestamp("last_updated").toLocalDateTime(),
                rs.getLong("warehouse_id"),
                rs.getLong("product_id")
        );
    }

    @Override
    public void insert(Inventory inventory) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inventory.getStockQuantity());
            ps.setTimestamp(2, Timestamp.valueOf(inventory.getLastUpdated()));
            ps.setLong(3, inventory.getWarehouseId());
            ps.setLong(4, inventory.getProductId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    inventory.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            logger.error("Error inserting Inventory: {}", inventory, e);
        }
    }

    @Override
    public Inventory update(Inventory inventory) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setInt(1, inventory.getStockQuantity());
            ps.setTimestamp(2, Timestamp.valueOf(inventory.getLastUpdated()));
            ps.setLong(3, inventory.getWarehouseId());
            ps.setLong(4, inventory.getProductId());
            ps.setLong(5, inventory.getId());

            ps.executeUpdate();
            return inventory;

        } catch (SQLException e) {
            logger.error("Error updating Inventory: {}", inventory, e);
        }
        return null;
    }
}
