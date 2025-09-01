package com.solvd.delivery.dao;

import com.solvd.delivery.models.Promotion;

public interface IPromotionDAO<T extends Promotion> extends IBaseDAO<T> {

    T getByCode(String code);
}
