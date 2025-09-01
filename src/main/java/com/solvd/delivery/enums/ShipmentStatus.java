package com.solvd.delivery.enums;

public enum ShipmentStatus {
    DELIVERED(1, "Delivered"),
    DELAYED(2, "Delayed"),
    RETURNED(3, "Returned");

    private final int id;
    private final String label;

    ShipmentStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }

    public static ShipmentStatus fromLabel(String label) {
        for (ShipmentStatus s : values()) {
            if (s.label.equalsIgnoreCase(label)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown ShipmentStatus label: " + label);
    }
}
