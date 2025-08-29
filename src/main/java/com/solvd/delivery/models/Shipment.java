package com.solvd.delivery.models;

import com.solvd.delivery.enums.ShipmentStatus;
import java.time.LocalDateTime;

public class Shipment {
    private long id;
    private LocalDateTime shipmentDate;
    private LocalDateTime deliveryDate;
    private ShipmentStatus status;
    private long orderId;

    public Shipment() {}

    public Shipment(long id, LocalDateTime shipmentDate, LocalDateTime deliveryDate, ShipmentStatus status, long orderId) {
        this.id = id;
        this.shipmentDate = shipmentDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.orderId = orderId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDateTime getShipmentDate() { return shipmentDate; }
    public void setShipmentDate(LocalDateTime shipmentDate) { this.shipmentDate = shipmentDate; }

    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDateTime deliveryDate) { this.deliveryDate = deliveryDate; }

    public ShipmentStatus getStatus() { return status; }
    public void setStatus(ShipmentStatus status) { this.status = status; }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }
}
