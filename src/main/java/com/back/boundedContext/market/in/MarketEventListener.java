package com.back.boundedContext.market.in;

import com.back.boundedContext.market.app.MarketFacade;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.event.MarketMemberCreatedEvent;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * 다른 컨텍스트에서 발행된 이벤트를 처리해 마켓 컨텍스트의 데이터를 동기화한다.
 *
 * 모든 이벤트는 발행 트랜잭션이 정상 커밋된 뒤에 처리되며,
 * 각 처리 과정은 새로운 트랜잭션에서 실행된다.
 */
@Component
@RequiredArgsConstructor
public class MarketEventListener {
    private final MarketFacade marketFacade;

    /**
     * 회원 가입 이벤트를 받아 마켓 회원 정보를 생성하거나 동기화한다.
     */
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberJoinedEvent event) {
        marketFacade.syncMember(event.getMember());
    }

    /**
     * 회원 수정 이벤트를 받아 마켓 회원 정보를 최신 상태로 갱신한다.
     */
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberModifiedEvent event) {
        marketFacade.syncMember(event.getMember());
    }

    /**
     * 신규 마켓 회원 생성 이벤트를 받아 해당 회원의 장바구니를 생성한다
     */
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MarketMemberCreatedEvent event) {
        marketFacade.createCart(event.getMember());
    }

    /**
     * 지갑 결제 성공 이벤트를 받아 주문을 결제 완료 상태로 변경한다.
     * @param event
     */
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(CashOrderPaymentSucceededEvent event) {
        int orderId = event.getOrder().getId();
        marketFacade.completeOrderPayment(orderId);
    }

    /**
     * 지갑 결제 실패 이벤트를 받아 주문의 결제 요청 상태를 해제한다.
     * @param event
     */
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(CashOrderPaymentFailedEvent event) {
        int orderId = event.getOrder().getId();
        marketFacade.cancelOrderRequestPayment(orderId);
    }
}
