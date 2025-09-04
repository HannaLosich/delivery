package com.solvd.delivery.dao.mysqlImpl;

import com.solvd.delivery.dao.ISupportTicketDAO;
import com.solvd.delivery.enums.TicketPriority;
import com.solvd.delivery.enums.TicketStatus;
import com.solvd.delivery.models.SupportTicket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupportTicketDAO extends GenericDAO<SupportTicket> implements ISupportTicketDAO<SupportTicket> {

    private static final String INSERT_SQL =
            "INSERT INTO Support_tickets (subject, status, priority, created_at, updated_at, Users_user_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE Support_tickets SET subject=?, status=?, priority=?, updated_at=?, Users_user_id=? WHERE id=?";

    @Override
    protected String getTableName() {
        return "Support_tickets";
    }

    @Override
    protected SupportTicket mapRow(ResultSet rs) throws SQLException {
        return new SupportTicket(
                rs.getLong("id"),
                rs.getString("subject"),
                TicketStatus.valueOf(rs.getString("status").toUpperCase()),
                TicketPriority.valueOf(rs.getString("priority").toUpperCase()),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime(),
                rs.getLong("Users_user_id")
        );
    }

    @Override
    public void insert(SupportTicket ticket) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, ticket.getSubject());
            stmt.setString(2, ticket.getStatus().name().toLowerCase());
            stmt.setString(3, ticket.getPriority().name().toLowerCase());
            stmt.setTimestamp(4, Timestamp.valueOf(ticket.getCreatedAt()));
            stmt.setTimestamp(5, Timestamp.valueOf(ticket.getUpdatedAt()));
            stmt.setLong(6, ticket.getUserId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SupportTicket update(SupportTicket ticket) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, ticket.getSubject());
            stmt.setString(2, ticket.getStatus().name().toLowerCase());
            stmt.setString(3, ticket.getPriority().name().toLowerCase());
            stmt.setTimestamp(4, Timestamp.valueOf(ticket.getUpdatedAt()));
            stmt.setLong(5, ticket.getUserId());
            stmt.setLong(6, ticket.getId());
            stmt.executeUpdate();
            return ticket;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
