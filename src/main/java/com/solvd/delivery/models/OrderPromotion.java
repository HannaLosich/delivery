package com.solvd.delivery.models;

public class OrderPromotion {
    private long id;
    private long orderId;
    private long promotionId;

    public OrderPromotion() {}

    public OrderPromotion(long id, long orderId, long promotionId) {
        this.id = id;
        this.orderId = orderId;
        this.promotionId = promotionId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }

    public long getPromotionId() { return promotionId; }
    public void setPromotionId(long promotionId) { this.promotionId = promotionId; }
}

