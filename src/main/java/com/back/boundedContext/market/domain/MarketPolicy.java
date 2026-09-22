package com.back.boundedContext.market.domain;

import org.springframework.beans.factory.annotation.Value;

/**
 * 판매정책
 * - 판매가 90%는 판매자의 몫, 10%는 수수료이다.
 */
public class MarketPolicy {
    public static double PRODUCT_PAYOUT_RATE;

    @Value("${custom.market.product.payoutRate}")
    public void setProductPayoutRate(double rate) {
        PRODUCT_PAYOUT_RATE = rate;
    }
}