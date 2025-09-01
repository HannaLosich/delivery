package com.solvd.delivery.dao;

import com.solvd.delivery.models.OrderPromotion;
import java.util.List;

public interface IOrderPromotionDAO<T> {
    void insert(T entity);
    T getById(long id);
    List<T> getAll();
    void update(T entity);
    void removeById(long id);
}
