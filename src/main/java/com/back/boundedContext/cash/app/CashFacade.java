package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashFacade {
    private final CashSupport cashSupport;
    private final CashSyncMemberUseCase cashSyncMemberUseCase;
    private final CashCreateWalletUseCase cashCreateWalletUseCase;
    private final CashCompleteOrderPaymentUseCase cashCompleteOrderPaymentUseCase;

    /**
     * 회원 정보를 캐시 회원 정보와 동기화한다.
     *
     * @param member
     * @return
     */
    @Transactional
    public CashMember syncMember(MemberDto member) {
        return cashSyncMemberUseCase.syncMember(member);
    }

    /**
     * 캐시 회원을 소유자로 하는 지갑을 생성한다.
     *
     * @param holder
     * @return
     */
    @Transactional
    public Wallet createWallet(CashMemberDto holder) {
        return cashCreateWalletUseCase.createWallet(holder);
    }

    /**
     * 주문결제요청을 처리한다.
     *
     * PG 결제 금액이 있으면 지갑에 먼저 충전한 뒤, 주문 금액을 지갑에서 차감하여 홀딩 지갑으로 옮긴다.
     * 처리 결과에 따라 주문 결제 성공/실패 이벤트를 반환한다.
     *
     * @param event
     */
    @Transactional
    public void handle(MarketOrderPaymentRequestedEvent event) {
        cashCompleteOrderPaymentUseCase.handle(event);
    }

    @Transactional(readOnly = true)
    public Optional<CashMember> findMemberByUsername(String username) {
        return cashSupport.findMemberByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Wallet> findWalletByHolder(CashMember holder) {
        return cashSupport.findWalletByHolder(holder);
    }

    @Transactional(readOnly = true)
    public Optional<Wallet> findWalletByHolderId(int holderId) {
        return cashSupport.findWalletByHolderId(holderId);
    }
}