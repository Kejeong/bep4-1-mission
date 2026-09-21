package com.back.boundedContext.market.app;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Product;
import com.back.global.rsData.RsData;
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

    /**
     * 멤버 동기화
     * @param member
     * @return
     */
    @Transactional
    public MarketMember syncMember(MemberDto member) {
        return marketSyncMemberUseCase.syncMember(member);
    }

    /**
     * 상품건수 확인
     * @return
     */
    @Transactional(readOnly = true)
    public long productsCount() {
        return marketSupport.countProducts();
    }

    /**
     * 상품등록
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

    @Transactional(readOnly = true)
    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketSupport.findMemberByUsername(username);
    }

    /**
     * 장바구니 생성
     * @param buyer
     * @return
     */
    @Transactional
    public RsData<Cart> createCart(MarketMemberDto buyer) {
        return marketCreateCartUseCase.createCart(buyer);
    }

    @Transactional(readOnly = true)
    public Optional<Cart> findCartByBuyer(MarketMember buyer) {
        return marketSupport.findCartByBuyer(buyer);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(int id) {
        return marketSupport.findProductById(id);
    }

}
