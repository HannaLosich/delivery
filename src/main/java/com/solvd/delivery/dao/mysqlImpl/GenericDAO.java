package com.solvd.delivery.dao.mysqlImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> extends AMySQLDB {

    protected abstract String getTableName(); // Table name provided by subclass
    protected abstract T mapRow(ResultSet rs) throws SQLException; // Row mapping implemented by subclass

    // ====== Common Methods ======

    public T getById(long id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return results;
    }

    public void removeById(long id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  NOTE: insert() and update() are NOT generic
    // because they depend on entity-specific fields
    // Subclasses will still implement those themselves.
}
