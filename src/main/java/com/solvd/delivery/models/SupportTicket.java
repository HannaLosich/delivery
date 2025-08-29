package com.solvd.delivery.models;

import com.solvd.delivery.enums.TicketPriority;
import com.solvd.delivery.enums.TicketStatus;
import java.time.LocalDateTime;

public class SupportTicket {
    private long id;
    private String subject;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long userId;

    public SupportTicket() {}

    public SupportTicket(long id, String subject, TicketStatus status, TicketPriority priority, LocalDateTime createdAt, LocalDateTime updatedAt, long userId) {
        this.id = id;
        this.subject = subject;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public TicketPriority getPriority() { return priority; }
    public void setPriority(TicketPriority priority) { this.priority = priority; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
}
