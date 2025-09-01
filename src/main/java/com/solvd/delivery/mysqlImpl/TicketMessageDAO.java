package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.ITicketMessageDAO;
import com.solvd.delivery.models.TicketMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketMessageDAO extends AMySQLDB implements ITicketMessageDAO<TicketMessage> {

    @Override
    public TicketMessage getById(long id) {
        String query = "SELECT * FROM TicketMessages WHERE id = ?";
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
    public List<TicketMessage> getAll() {
        List<TicketMessage> messages = new ArrayList<>();
        String query = "SELECT * FROM TicketMessages";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) messages.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public void insert(TicketMessage message) {
        String query = "INSERT INTO TicketMessages (message_text, created_at, user_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, message.getMessageText());
            ps.setTimestamp(2, Timestamp.valueOf(message.getCreatedAt()));
            ps.setLong(3, message.getUserId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) message.setId(rs.getLong(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TicketMessage update(TicketMessage message) {
        String query = "UPDATE TicketMessages SET message_text = ?, created_at = ?, user_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, message.getMessageText());
            ps.setTimestamp(2, Timestamp.valueOf(message.getCreatedAt()));
            ps.setLong(3, message.getUserId());
            ps.setLong(4, message.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;  // return the updated object to match IBaseDAO
    }



    @Override
    public void removeById(long id) {
        String query = "DELETE FROM TicketMessages WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TicketMessage mapRow(ResultSet rs) throws SQLException {
        return new TicketMessage(
                rs.getLong("id"),
                rs.getString("message_text"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getLong("user_id")
        );
    }
}
