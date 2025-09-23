package com.solvd.delivery.enums;

import com.solvd.delivery.exceptions.UnknownTrackingStatusException;

public enum TrackingStatus {
    OPENED(1, "Opened", "opened"),
    PROCESSING(2, "Processing", "processing"),
    DELIVERED(3, "Delivered", "delivered"),
    CLOSED(4, "Closed", "closed"),
    CREATED(5, "Created", "created"),
    IN_TRANSIT(6, "In Transit", "in_transit"),
    DELAYED(7, "Delayed", "delayed");

    private final int id;
    private final String label;
    private final String dbValue;

    TrackingStatus(int id, String label, String dbValue) {
        this.id = id;
        this.label = label;
        this.dbValue = dbValue;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }
    public String getDbValue() { return dbValue; }

    public static TrackingStatus fromLabel(String label) throws UnknownTrackingStatusException {
        for (TrackingStatus status : values()) {
            if (status.label.equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new UnknownTrackingStatusException("Unknown TrackingStatus label: " + label);
    }

    public static TrackingStatus fromId(int id) throws UnknownTrackingStatusException {
        for (TrackingStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new UnknownTrackingStatusException("Unknown TrackingStatus id: " + id);
    }

    public static TrackingStatus fromDbValue(String dbValue) throws UnknownTrackingStatusException {
        for (TrackingStatus status : values()) {
            if (status.dbValue.equalsIgnoreCase(dbValue)) {
                return status;
            }
        }
        throw new UnknownTrackingStatusException("Unknown TrackingStatus dbValue: " + dbValue);
    }
}
