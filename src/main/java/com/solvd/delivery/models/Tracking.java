package com.solvd.delivery.models;

import com.solvd.delivery.enums.TrackingStatus;
import java.time.LocalDateTime;

public class Tracking {
    private long id;
    private String trackingNumber;
    private TrackingStatus status;
    private LocalDateTime lastUpdate;
    private long shipmentId;

    public Tracking() {}

    public Tracking(long id, String trackingNumber, TrackingStatus status, LocalDateTime lastUpdate, long shipmentId) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.lastUpdate = lastUpdate;
        this.shipmentId = shipmentId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public TrackingStatus getStatus() { return status; }
    public void setStatus(TrackingStatus status) { this.status = status; }

    public LocalDateTime getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }

    public long getShipmentId() { return shipmentId; }
    public void setShipmentId(long shipmentId) { this.shipmentId = shipmentId; }
}
