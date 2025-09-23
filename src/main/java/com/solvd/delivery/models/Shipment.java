package com.solvd.delivery.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.solvd.delivery.enums.ShipmentStatus;
import com.solvd.delivery.utils.LocalDateTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "shipment")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipment {

    @XmlAttribute
    private long id;

    @XmlElement(name = "shipmentDate")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime shipmentDate;

    @XmlElement(name = "deliveryDate")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime deliveryDate;

    @XmlElement(name = "status")
    private ShipmentStatus status;

    @XmlElement(name = "orderId")
    private long orderId;

    // Relationships
    @XmlElement(name = "trackings")
    private List<Tracking> trackings;

    @XmlElement(name = "reviews")
    private List<Review> reviews;

    // Default constructor
    public Shipment() {}

    public Shipment(long id, LocalDateTime shipmentDate, LocalDateTime deliveryDate,
                    ShipmentStatus status, long orderId) {
        this.id = id;
        this.shipmentDate = shipmentDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.orderId = orderId;
    }

    // ===== Getters & Setters =====

    @JsonGetter("id")
    public long getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonGetter("shipmentDate")
    public LocalDateTime getShipmentDate() {
        return shipmentDate;
    }

    @JsonSetter("shipmentDate")
    public void setShipmentDate(LocalDateTime shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    @JsonGetter("deliveryDate")
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    @JsonSetter("deliveryDate")
    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @JsonGetter("status")
    public ShipmentStatus getStatus() {
        return status;
    }

    @JsonSetter("status")
    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    @JsonGetter("orderId")
    public long getOrderId() {
        return orderId;
    }

    @JsonSetter("orderId")
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @JsonGetter("trackings")
    public List<Tracking> getTrackings() {
        return trackings;
    }

    @JsonSetter("trackings")
    public void setTrackings(List<Tracking> trackings) {
        this.trackings = trackings;
    }

    @JsonGetter("reviews")
    public List<Review> getReviews() {
        return reviews;
    }

    @JsonSetter("reviews")
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", shipmentDate=" + shipmentDate +
                ", deliveryDate=" + deliveryDate +
                ", status=" + status +
                ", orderId=" + orderId +
                ", trackings=" + (trackings != null ? trackings.size() : 0) +
                ", reviews=" + (reviews != null ? reviews.size() : 0) +
                '}';
    }
}
