package com.solvd.delivery.services.interfaces;

import com.solvd.delivery.models.Shipment;

public interface IDeliveryService {

    double calculateEstimatedDeliveryTime(Shipment shipment);
    boolean isDelayed(Shipment shipment);
}
