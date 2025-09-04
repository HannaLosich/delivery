package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IProductDAO;
import com.solvd.delivery.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends GenericDAO<Product> implements IProductDAO<Product> {

    private static final String INSERT_SQL =
            "INSERT INTO Products (name, description, price, sku) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE Products SET name=?, description=?, price=?, sku=? WHERE id=?";
    private static final String SELECT_BY_SKU_SQL =
            "SELECT * FROM Products WHERE sku=?";

    @Override
    protected String getTableName() {
        return "Products";
    }

    @Override
    protected Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getString("sku")
        );
    }

    @Override
    public void insert(Product product) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getSku());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    product.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product update(Product product) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getSku());
            stmt.setLong(5, product.getId());

            stmt.executeUpdate();
            return product;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getBySku(String sku) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_SKU_SQL)) {

            stmt.setString(1, sku);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
