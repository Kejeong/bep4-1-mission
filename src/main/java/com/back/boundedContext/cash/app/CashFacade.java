package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.boundedContext.cash.out.WalletRepository;
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
     * 멤버 동기화
     * @param member
     * @return
     */
    @Transactional
    public CashMember syncMember(MemberDto member) {
        return cashSyncMemberUseCase.syncMember(member);
    }

    /**
     * 지갑 생성
     * @param holder
     * @return
     */
    @Transactional
    public Wallet createWallet(CashMemberDto holder) {
        return cashCreateWalletUseCase.createWallet(holder);
    }

    /**
     * 멤버 조회
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<CashMember> findMemberByUsername(String username) {
        return cashSupport.findMemberByUsername(username);
    }

    /**
     * 홀딩된 금액 조회
     * @param holder
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<Wallet> findWalletByHolder(CashMember holder) {
        return cashSupport.findWalletByHolder(holder);
    }

    /**
     * 주문결제요청
     * @param event
     */
    @Transactional
    public void handle(MarketOrderPaymentRequestedEvent event) {
        cashCompleteOrderPaymentUseCase.handle(event);
    }

    @Transactional(readOnly = true)
    public Optional<Wallet> findWalletByHolderId(int holderId) {
        return cashSupport.findWalletByHolderId(holderId);
    }
}