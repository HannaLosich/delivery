package com.solvd.delivery.models;

import java.time.LocalDateTime;

public class Review {
    private long id;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private long userId;
    private long shipmentId;

    public Review() {}

    public Review(long id, int rating, String comment, LocalDateTime createdAt, long userId, long shipmentId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.userId = userId;
        this.shipmentId = shipmentId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getShipmentId() { return shipmentId; }
    public void setShipmentId(long shipmentId) { this.shipmentId = shipmentId; }
}
