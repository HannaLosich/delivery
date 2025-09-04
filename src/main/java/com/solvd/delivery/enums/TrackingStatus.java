package com.solvd.delivery.enums;

import com.solvd.delivery.exceptions.UnknownTrackingStatusException;

public enum TrackingStatus {
    CREATED(1, "Created"),
    IN_TRANSIT(2, "In Transit"),
    DELIVERED(3, "Delivered"),
    PROCESSING(4, "Processing"),
    DELAYED(5, "Delayed");

    private final int id;
    private final String label;

    TrackingStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }

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
}
