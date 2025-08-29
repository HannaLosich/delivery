package com.solvd.delivery.models;

import java.time.LocalDateTime;

public class Inventory {
    private long id;
    private int stockQuantity;
    private LocalDateTime lastUpdated;
    private long warehouseId;
    private long productId;

    public Inventory() {}

    public Inventory(long id, int stockQuantity, LocalDateTime lastUpdated, long warehouseId, long productId) {
        this.id = id;
        this.stockQuantity = stockQuantity;
        this.lastUpdated = lastUpdated;
        this.warehouseId = warehouseId;
        this.productId = productId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(long warehouseId) { this.warehouseId = warehouseId; }

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }
}
