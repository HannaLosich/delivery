package com.solvd.delivery.enums;

public enum TicketPriority {
    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High"),
    URGENT(4, "Urgent");

    private final int id;
    private final String label;

    TicketPriority(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }
}
