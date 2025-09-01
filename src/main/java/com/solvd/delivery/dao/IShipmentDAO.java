package com.solvd.delivery.dao;

import com.solvd.delivery.models.Shipment;
import java.util.List;

public interface IShipmentDAO<T extends Shipment> extends IBaseDAO<T> {
    List<T> getByOrderId(long orderId);
}
