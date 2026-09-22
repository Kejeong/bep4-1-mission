package com.back.boundedContext.market.app;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.domain.Product;
import com.back.global.rsData.RsData;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketFacade {
    private final MarketCreateProductUseCase marketCreateProductUseCase;
    private final MarketCreateCartUseCase marketCreateCartUseCase;
    private final MarketSyncMemberUseCase marketSyncMemberUseCase;
    private final MarketSupport marketSupport;
    private final MarketCreateOrderUseCase marketCreateOrderUseCase;
    private final MarketCompleteOrderPaymentUseCase marketCompleteOrderPaymentUseCase;
    private final MarketCancelOrderRequestPaymentUseCase marketCancelOrderRequestPaymentUseCase;

    @Transactional(readOnly = true)
    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketSupport.findMemberByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Cart> findCartByBuyer(MarketMember buyer) {
        return marketSupport.findCartByBuyer(buyer);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(int id) {
        return marketSupport.findProductById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findOrderById(int id) {
        return marketSupport.findOrderById(id);
    }

    /**
     * 회원 서비스의 회원 정보를 마켓 컨텍스트의 회원 정보와 동기화한다.
     *
     * @param member
     * @return
     */
    @Transactional
    public MarketMember syncMember(MemberDto member) {
        return marketSyncMemberUseCase.syncMember(member);
    }

    /**
     * 등록된 상품의 건 수를 반환한다.
     *
     * @return
     */
    @Transactional(readOnly = true)
    public long productsCount() {
        return marketSupport.countProducts();
    }

    /**
     * 상품을 등록한다.
     *
     * @param seller
     * @param sourceTypeCode
     * @param sourceId
     * @param name
     * @param description
     * @param price
     * @param salePrice
     * @return
     */
    @Transactional
    public Product createProduct(
            MarketMember seller,
            String sourceTypeCode,
            int sourceId,
            String name,
            String description,
            long price,
            long salePrice
    ) {
        return marketCreateProductUseCase.createProduct(
                seller,
                sourceTypeCode,
                sourceId,
                name,
                description,
                price,
                salePrice
        );
    }

    /**
     * 마켓 회원의 장바구니를 생성한다.
     *
     * @param buyer
     * @return
     */
    @Transactional
    public RsData<Cart> createCart(MarketMemberDto buyer) {
        return marketCreateCartUseCase.createCart(buyer);
    }

    /**
     * 주문 건수를 반환한다.
     *
     * @return
     */
    @Transactional(readOnly = true)
    public long ordersCount() {
        return marketSupport.countOrders();
    }

    /**
     * 장바구니의 현재 상품을 기준으로 주문을 생성한다.
     *
     * @param cart
     * @return
     */
    @Transactional
    public RsData<Order> createOrder(Cart cart) {
        return marketCreateOrderUseCase.createOrder(cart);
    }

    /**
     * 주문의 지갑 결제를 요청한다.
     *
     * 주문을 결제 진행 상태로 변경하고 결제 요청 이벤트를 발행한다.
     * 실제 지갑 차감 및 주문 완료 처리는 이벤트 리스너에서 후속 처리된다.
     * @param order
     * @param pgPaymentAmount
     */
    @Transactional
    public void requestPayment(Order order, long pgPaymentAmount) {
        order.requestPayment(pgPaymentAmount);
    }

    /**
     * 지갑 결제 성공 이벤트를 처리해 주문을 결제 완료 상태로 변경한다.
     *
     * @param event
     */
    @Transactional
    public void handle(CashOrderPaymentSucceededEvent event) {
        marketCompleteOrderPaymentUseCase.handle(event);
    }

    /**
     * 지갑 결제 실패 이벤트를 처리해 주문의 결제 요청 상태를 해제한다.
     *
     * @param event
     */
    @Transactional
    public void handle(CashOrderPaymentFailedEvent event) {
        marketCancelOrderRequestPaymentUseCase.handle(event);
    }
}
