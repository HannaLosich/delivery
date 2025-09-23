package com.solvd.delivery.dao;

import com.solvd.delivery.models.Product;
import java.util.List;

public interface IProductDAO<T extends Product> extends IBaseDAO<T> {

    T getBySku(String sku);

    List<T> getByOrderId(long orderId);
}
