package com.solvd.delivery.models;

public class Address {
    private long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private long userId;

    public Address() {}

    public Address(long id, String street, String city, String state, String country, String postalCode, long userId) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.userId = userId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
}
