package com.back.boundedContext.member.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class MemberPolicy {  // 정책들은 별도의 클래스에 모아놔야 한다.
    private static int PASSWORD_CHANGE_DAYS = 90;

    @Value("${custom.member.password.changeDays}")
    public void setPasswordChangeDays(int days) {
        PASSWORD_CHANGE_DAYS = days;
    }

    public Duration getNeedToChangePasswordPeriod() {
        return Duration.ofDays(PASSWORD_CHANGE_DAYS);
    }
    // 비밀번호가 몇일 남았는지 명세작성
    public int getNeedToChangePasswordDays() {
        return PASSWORD_CHANGE_DAYS;
    }

    public boolean isNeedToChangePassword(LocalDateTime lastChangeDate) {
        if (lastChangeDate == null) return true;

        return lastChangeDate.plusDays(PASSWORD_CHANGE_DAYS)
                .isBefore(LocalDateTime.now());
    }
}