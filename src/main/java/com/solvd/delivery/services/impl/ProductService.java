package com.solvd.delivery.services.impl;

import com.solvd.delivery.dao.mysqlImpl.ProductDAO;
import com.solvd.delivery.dao.mysqlImpl.PromotionDAO;
import com.solvd.delivery.enums.DiscountType;
import com.solvd.delivery.models.Product;
import com.solvd.delivery.models.Promotion;
import com.solvd.delivery.services.interfaces.IProductService;

import java.util.List;

public class ProductService implements IProductService {

    private final ProductDAO productDAO = new ProductDAO();
    private final PromotionDAO promotionDAO = new PromotionDAO();

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    @Override
    public boolean applyPromotionToProduct(String sku, String promoCode) {
        Product product = productDAO.getBySku(sku);
        Promotion promo = promotionDAO.getByCode(promoCode);

        if (product != null && promo != null) {
            DiscountType discountType = promo.getDiscountType();


            switch (discountType) {
                case PERCENTAGE:
                    System.out.println("Applying " + promo.getDiscountValue() + "% discount to product " + product.getName());
                    break;
                case FIXED_AMOUNT:
                    System.out.println("Applying fixed discount of " + promo.getDiscountValue() + " to product " + product.getName());
                    break;
                case FREE_SHIPPING:
                    System.out.println("Applying free shipping promotion to product " + product.getName());
                    break;
                default:
                    System.out.println("Unknown promotion type for " + product.getName());
            }

            return true; // indicate promotion applied successfully
        }

        return false; // product or promotion not found
    }
}
