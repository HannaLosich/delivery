package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IWarehouseDAO;
import com.solvd.delivery.models.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class WarehouseDAO extends GenericDAO<Warehouse> implements IWarehouseDAO<Warehouse> {

    private static final Logger logger = LogManager.getLogger(WarehouseDAO.class);
    private static final String INSERT_SQL =
            "INSERT INTO Warehouses (name, location) VALUES (?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE Warehouses SET name = ?, location = ? WHERE id=?";

    @Override
    protected String getTableName() {
        return "Warehouses";
    }

    @Override
    protected Warehouse mapRow(ResultSet rs) throws SQLException {
        return new Warehouse(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("location")
        );
    }

    @Override
    public void insert(Warehouse warehouse) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, warehouse.getName());
            stmt.setString(2, warehouse.getLocation());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    warehouse.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            logger.error("Error inserting Warehouse: {}", warehouse, e);
        }

    }

    @Override
    public Warehouse update(Warehouse warehouse) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, warehouse.getName());
            stmt.setString(2, warehouse.getLocation());
            stmt.setLong(3, warehouse.getId());
            stmt.executeUpdate();
            return warehouse;

        } catch (SQLException e) {
            logger.error("Error updating Warehouse: {}", warehouse, e);
        }

        return null;
    }
}
