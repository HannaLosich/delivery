package com.solvd.delivery.enums;

public enum TicketStatus {
    OPEN(1, "Open"),
    IN_PROGRESS(2, "In Progress"),
    RESOLVED(3, "Resolved"),
    PROCESSING(4, "Processing"),
    CLOSED(5, "Closed");

    private final int id;
    private final String label;

    TicketStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }
}
