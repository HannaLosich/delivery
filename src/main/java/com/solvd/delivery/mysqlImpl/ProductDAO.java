package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.IProductDAO;
import com.solvd.delivery.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends AMySQLDB implements IProductDAO<Product> {

    private static final String INSERT_SQL =
            "INSERT INTO Products (name, description, price, sku) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM Products WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT * FROM Products";
    private static final String UPDATE_SQL =
            "UPDATE Products SET name=?, description=?, price=?, sku=? WHERE id=?";
    private static final String DELETE_SQL =
            "DELETE FROM Products WHERE id=?";
    private static final String SELECT_BY_SKU_SQL =
            "SELECT * FROM Products WHERE sku=?";

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
    public Product getById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapProduct(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
            while (rs.next()) {
                products.add(mapProduct(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
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
    public void removeById(long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product getBySku(String sku) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_SKU_SQL)) {

            stmt.setString(1, sku);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapProduct(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getString("sku")
        );
    }
}
