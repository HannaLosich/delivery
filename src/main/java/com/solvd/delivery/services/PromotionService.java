package com.solvd.delivery.services;

import com.solvd.delivery.models.Promotion;
import com.solvd.delivery.models.User;

import com.solvd.delivery.services.interfaces.IPromotionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PromotionService implements IPromotionService {

    private static final Logger LOGGER = LogManager.getLogger();

    private String promoCode;
    private int deliveryCount;  // e.g., how many deliveries user has completed
    private User user;
    private Promotion promotion;

    public PromotionService() {}

    public PromotionService(String promoCode, int deliveryCount, User user, Promotion promotion) {
        this.promoCode = promoCode;
        this.deliveryCount = deliveryCount;
        this.user = user;
        this.promotion = promotion;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public int getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(int deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    private boolean isValidPromoCode() {
        if (promotion == null || promoCode == null) return false;
        return promoCode.equalsIgnoreCase(promotion.getCode());
    }

    @Override
    public double calculateDiscount() {
        boolean firstDelivery = deliveryCount == 1;
        boolean everyThirdDelivery = deliveryCount > 0 && deliveryCount % 3 == 0; // example
        boolean validPromo = isValidPromoCode();

        if (firstDelivery) return 0.15;       // 15% for first delivery
        if (everyThirdDelivery) return 0.05;   // 5% every 3rd delivery
        if (validPromo) return promotion.getDiscountValue(); // promotion-defined discount

        return 0.0;
    }

    public void printPromotionDetails() {
        LOGGER.info("User: " + user.getFirstName() + " " + user.getLastName());
        LOGGER.info("Delivery Count: " + deliveryCount);
        LOGGER.info("Promo Code Entered: " + promoCode);
        LOGGER.info("Discount Applied: " + (calculateDiscount() * 100) + "%");
    }
}
