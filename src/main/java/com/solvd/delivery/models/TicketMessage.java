package com.solvd.delivery.models;

import java.time.LocalDateTime;

public class TicketMessage {
    private long id;
    private String messageText;
    private LocalDateTime createdAt;
    private long userId;

    public TicketMessage() {}

    public TicketMessage(long id, String messageText, LocalDateTime createdAt, long userId) {
        this.id = id;
        this.messageText = messageText;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
}
