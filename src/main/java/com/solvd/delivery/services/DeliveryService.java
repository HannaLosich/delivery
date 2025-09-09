package com.solvd.delivery.services;

import com.solvd.delivery.models.Shipment;
import com.solvd.delivery.enums.ShipmentStatus;
import com.solvd.delivery.services.interfaces.IDeliveryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;

public class DeliveryService implements IDeliveryService {

    private static final Logger LOGGER = LogManager.getLogger("DeliveryService");

    @Override
    public double calculateEstimatedDeliveryTime(Shipment shipment) {
        if (shipment == null) {
            LOGGER.warn("Shipment is null, returning 0.0 hours");
            return 0.0;
        }

        LocalDateTime shipmentDate = shipment.getShipmentDate();
        LocalDateTime deliveryDate = shipment.getDeliveryDate();

        if (shipmentDate == null || deliveryDate == null) {
            LOGGER.warn("Shipment or delivery date is null for shipment ID: {}", shipment.getId());
            return 0.0;
        }

        Duration duration = Duration.between(shipmentDate, deliveryDate);
        double hours = duration.toHours();
        LOGGER.info("Estimated delivery time for shipment ID {}: {} hours", shipment.getId(), hours);
        return hours;
    }

    @Override
    public boolean isDelayed(Shipment shipment) {
        if (shipment == null) {
            LOGGER.warn("Shipment is null, cannot check delay");
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deliveryDate = shipment.getDeliveryDate();

        if (deliveryDate == null) {
            LOGGER.warn("Delivery date is null for shipment ID: {}", shipment.getId());
            return false;
        }

        boolean delayed = now.isAfter(deliveryDate) && shipment.getStatus() != ShipmentStatus.DELIVERED;

        LOGGER.info("Shipment ID {} delayed: {}", shipment.getId(), delayed);
        return delayed;
    }
}
