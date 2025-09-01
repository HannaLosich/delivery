package com.solvd.delivery.enums;

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

    public static TrackingStatus fromLabel(String label) {
        for (TrackingStatus status : values()) {
            if (status.label.equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown TrackingStatus label: " + label);
    }

    public static TrackingStatus fromId(int id) {
        for (TrackingStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown TrackingStatus id: " + id);
    }
}
