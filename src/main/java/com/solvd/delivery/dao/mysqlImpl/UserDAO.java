package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.IUserDAO;
import com.solvd.delivery.models.User;

import java.sql.*;
import java.util.List;

public class UserDAO extends GenericDAO<User> implements IUserDAO<User> {

    private static final String INSERT_SQL =
            "INSERT INTO Users (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE Users SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE id=?";
    private static final String SELECT_BY_EMAIL_SQL =
            "SELECT * FROM Users WHERE email = ?";
    private static final String SELECT_BY_PHONE_SQL =
            "SELECT * FROM Users WHERE phone_number = ?";

    @Override
    protected String getTableName() {
        return "Users";
    }

    @Override
    protected User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        return user;
    }

    @Override
    public void insert(User user) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User update(User user) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setLong(5, user.getId());
            stmt.executeUpdate();
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_EMAIL_SQL)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PHONE_SQL)) {

            stmt.setString(1, phoneNumber);
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
