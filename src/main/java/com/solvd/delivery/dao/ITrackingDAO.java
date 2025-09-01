package com.solvd.delivery.dao;

import com.solvd.delivery.models.Tracking;
import java.util.List;

public interface ITrackingDAO<T extends Tracking> extends IBaseDAO<T> {
    List<T> getByShipmentId(long shipmentId);
}
