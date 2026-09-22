package com.back.boundedContext.payout.app;

import com.back.boundedContext.payout.domain.Payout;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.dto.PayoutMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayoutFacade {
    private final PayoutSyncMemberUseCase payoutSyncMemberUseCase;
    private final PayoutCreatePayoutUseCase payoutCreatePayoutUseCase;
    private final PayoutAddPayoutCandidateItemsUseCase payoutAddPayoutCandidateItemsUseCase;

    /**
     * 회원정보를 정산회원이랑 동기화한다.
     */
    @Transactional
    public void syncMember(MemberDto member) {
        payoutSyncMemberUseCase.syncMember(member);
    }

    /**
     * 정산을 생성한다.
     */
    @Transactional
    public Payout createPayout(PayoutMemberDto payee) {
        return payoutCreatePayoutUseCase.createPayout(payee);
    }

    /**
     * 결제가 완료된 주문의 상품들을 정산 후보로 등록
     */
    @Transactional
    public void addPayoutCandidateItems(OrderDto order) {
        payoutAddPayoutCandidateItemsUseCase.addPayoutCandidateItems(order);
    }
}