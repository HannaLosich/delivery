package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.ITicketMessageDAO;
import com.solvd.delivery.models.TicketMessage;

import java.sql.*;
import java.util.List;

public class TicketMessageDAO extends GenericDAO<TicketMessage> implements ITicketMessageDAO<TicketMessage> {

    private static final String INSERT_SQL =
            "INSERT INTO TicketMessages (message_text, created_at, user_id) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE TicketMessages SET message_text=?, created_at=?, user_id=? WHERE id=?";

    @Override
    protected String getTableName() {
        return "TicketMessages";
    }

    @Override
    protected TicketMessage mapRow(ResultSet rs) throws SQLException {
        return new TicketMessage(
                rs.getLong("id"),
                rs.getString("message_text"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getLong("user_id")
        );
    }

    @Override
    public void insert(TicketMessage message) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, message.getMessageText());
            stmt.setTimestamp(2, Timestamp.valueOf(message.getCreatedAt()));
            stmt.setLong(3, message.getUserId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    message.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TicketMessage update(TicketMessage message) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, message.getMessageText());
            stmt.setTimestamp(2, Timestamp.valueOf(message.getCreatedAt()));
            stmt.setLong(3, message.getUserId());
            stmt.setLong(4, message.getId());
            stmt.executeUpdate();
            return message;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
