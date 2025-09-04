package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IAddressDAO;
import com.solvd.delivery.models.Address;

import java.sql.*;

public class AddressDAO extends GenericDAO<Address> implements IAddressDAO<Address> {

    private static final String INSERT_SQL =
            "INSERT INTO addresses (street, city, state, country, postal_code, user_id) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE addresses SET street=?, city=?, state=?, country=?, postal_code=?, user_id=? WHERE id=?";

    private static final String SELECT_BY_USER_ID_SQL =
            "SELECT * FROM addresses WHERE user_id = ?";

    @Override
    protected String getTableName() {
        return "addresses";
    }

    @Override
    protected Address mapRow(ResultSet rs) throws SQLException {
        return new Address(
                rs.getLong("id"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("country"),
                rs.getString("postal_code"),
                rs.getLong("user_id")
        );
    }

    @Override
    public void insert(Address address) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, address.getStreet());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getState());
            stmt.setString(4, address.getCountry());
            stmt.setString(5, address.getPostalCode());
            stmt.setLong(6, address.getUserId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    address.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address update(Address address) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, address.getStreet());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getState());
            stmt.setString(4, address.getCountry());
            stmt.setString(5, address.getPostalCode());
            stmt.setLong(6, address.getUserId());
            stmt.setLong(7, address.getId());
            stmt.executeUpdate();
            return address;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address getByUserId(long userId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USER_ID_SQL)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
