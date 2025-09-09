package com.solvd.delivery.dao.mysqlImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> extends AMySQLDB {

    private static final Logger logger = LogManager.getLogger(GenericDAO.class);

    protected abstract String getTableName();          // Table name provided by subclass
    protected abstract T mapRow(ResultSet rs) throws SQLException; // Row mapping implemented by subclass

    // ====== Common Methods ======

    public T getById(long id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            logger.error("Error getting entity by ID {} from table {}", id, getTableName(), e);
        }
        return null;
    }

    public List<T> getAll() {
        List<T> results = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                results.add(mapRow(rs));
            }

        } catch (SQLException e) {
            logger.error("Error getting all entities from table {}", getTableName(), e);
        }
        return results;
    }

    public void removeById(long id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Removed {} row(s) with ID {} from table {}", rowsAffected, id, getTableName());

        } catch (SQLException e) {
            logger.error("Error removing entity by ID {} from table {}", id, getTableName(), e);
        }
    }

    // NOTE: insert() and update() are NOT generic
    // because they depend on entity-specific fields
    // Subclasses will implement those themselves.
}
