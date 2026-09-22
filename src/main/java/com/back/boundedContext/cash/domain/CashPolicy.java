package com.back.boundedContext.cash.domain;

import org.springframework.beans.factory.annotation.Value;

public class CashPolicy {
    public static int HOLDING_MEMBER_ID = 2;

    @Value("${custom.global.holdingMemberId}")
    public void setHoldingMemberId(int id) {
        HOLDING_MEMBER_ID = id;
    }
}
