package com.solvd.delivery.services.factories;

import com.solvd.delivery.enums.DiscountType;
import com.solvd.delivery.exceptions.UnknownDiscountTypeException;

public class DiscountTypeFactory {

    public static DiscountType getById(int id) throws UnknownDiscountTypeException {
        return DiscountType.fromId(id);
    }

    public static DiscountType getByLabel(String label) throws UnknownDiscountTypeException {
        return DiscountType.fromLabel(label);
    }
}
