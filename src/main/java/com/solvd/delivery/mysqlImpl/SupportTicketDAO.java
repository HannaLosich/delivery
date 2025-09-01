package com.solvd.delivery.mysqlImpl;

import com.solvd.delivery.dao.ISupportTicketDAO;
import com.solvd.delivery.enums.TicketPriority;
import com.solvd.delivery.enums.TicketStatus;
import com.solvd.delivery.models.SupportTicket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupportTicketDAO extends AMySQLDB implements ISupportTicketDAO<SupportTicket> {

    @Override
    public void insert(SupportTicket ticket) {
        String query = "INSERT INTO Support_tickets (subject, status, priority, created_at, updated_at, Users_user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ticket.getSubject());
            ps.setString(2, ticket.getStatus().name().toLowerCase());
            ps.setString(3, ticket.getPriority().name().toLowerCase());
            ps.setTimestamp(4, Timestamp.valueOf(ticket.getCreatedAt()));
            ps.setTimestamp(5, Timestamp.valueOf(ticket.getUpdatedAt()));
            ps.setLong(6, ticket.getUserId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) ticket.setId(rs.getLong(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SupportTicket getById(long id) {
        String query = "SELECT * FROM Support_tickets WHERE id = ?";
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
    public List<SupportTicket> getAll() {
        List<SupportTicket> tickets = new ArrayList<>();
        String query = "SELECT * FROM Support_tickets";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) tickets.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public SupportTicket update(SupportTicket ticket) {
        String query = "UPDATE Support_tickets SET subject = ?, status = ?, priority = ?, updated_at = ?, Users_user_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, ticket.getSubject());
            ps.setString(2, ticket.getStatus().name().toLowerCase());
            ps.setString(3, ticket.getPriority().name().toLowerCase());
            ps.setTimestamp(4, Timestamp.valueOf(ticket.getUpdatedAt()));
            ps.setLong(5, ticket.getUserId());
            ps.setLong(6, ticket.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket; // return the updated object
    }


    @Override
    public void removeById(long id) {
        String query = "DELETE FROM Support_tickets WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SupportTicket mapRow(ResultSet rs) throws SQLException {
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
}
