package com.solvd.delivery.services.interfaces;

import com.solvd.delivery.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    boolean applyPromotionToProduct(String sku, String promoCode);
}
