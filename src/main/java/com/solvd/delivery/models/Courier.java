package com.solvd.delivery.models;

public class Courier {
    private long id;
    private String name;
    private String contactNumber;
    private String email;
    private long shipmentId;
    private long userId;

    public Courier() {}

    public Courier(long id, String name, String contactNumber, String email, long shipmentId, long userId) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.shipmentId = shipmentId;
        this.userId = userId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getShipmentId() { return shipmentId; }
    public void setShipmentId(long shipmentId) { this.shipmentId = shipmentId; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
}
