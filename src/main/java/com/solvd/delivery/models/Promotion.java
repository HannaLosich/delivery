package com.solvd.delivery.models;

import com.solvd.delivery.enums.DiscountType;
import java.time.LocalDateTime;

public class Promotion {
    private long id;
    private String code;
    private String description;
    private DiscountType discountType;
    private double discountValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion() {}

    public Promotion(long id, String code, String description, DiscountType discountType, double discountValue, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public DiscountType getDiscountType() { return discountType; }
    public void setDiscountType(DiscountType discountType) { this.discountType = discountType; }

    public double getDiscountValue() { return discountValue; }
    public void setDiscountValue(double discountValue) { this.discountValue = discountValue; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
}
