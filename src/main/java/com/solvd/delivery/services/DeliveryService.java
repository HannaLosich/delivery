package com.solvd.delivery.services;

import com.solvd.delivery.models.Shipment;
import com.solvd.delivery.enums.ShipmentStatus;
import com.solvd.delivery.services.interfaces.IDeliveryService;

import java.time.Duration;
import java.time.LocalDateTime;

public class DeliveryService implements IDeliveryService {

    @Override
    public double calculateEstimatedDeliveryTime(Shipment shipment) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime shipmentDate = shipment.getShipmentDate();
        LocalDateTime deliveryDate = shipment.getDeliveryDate();

        if (shipmentDate == null || deliveryDate == null) return 0.0;

        Duration duration = Duration.between(shipmentDate, deliveryDate);
        return duration.toHours();
    }

    @Override
    public boolean isDelayed(Shipment shipment) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deliveryDate = shipment.getDeliveryDate();

        if (deliveryDate == null) return false;

        //  delayed if current time is after delivery date
        boolean delayed = now.isAfter(deliveryDate);

        //  apply for shipments that are not delivered
        return delayed && shipment.getStatus() != ShipmentStatus.DELIVERED;
    }
}
