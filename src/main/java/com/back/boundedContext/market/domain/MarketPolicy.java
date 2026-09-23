package com.back.boundedContext.market.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 판매정책
 * - 판매가 90%는 판매자의 몫, 10%는 수수료이다.
 */
@Component
public class MarketPolicy {
    public static double PRODUCT_PAYOUT_RATE;

    @Value("${custom.market.product.payoutRate}")
    public void setProductPayoutRate(double rate) {
        PRODUCT_PAYOUT_RATE = rate;
    }

    public static long calculatePayoutFee(long salePrice, double payoutRate) {
        return salePrice - calculateSalePriceWithoutFee(salePrice, payoutRate);
    }

    public static long calculateSalePriceWithoutFee(long salePrice, double payoutRate) {
        return Math.round(salePrice * payoutRate / 100);
    }
}