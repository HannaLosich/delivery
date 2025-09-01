package com.solvd.delivery.dao;

import com.solvd.delivery.models.Product;

public interface IProductDAO<T extends Product> extends IBaseDAO<T> {

    T getBySku(String sku);
}
