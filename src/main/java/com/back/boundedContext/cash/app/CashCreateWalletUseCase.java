package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.cash.out.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {
    private final WalletRepository walletRepository;

    /**
     * 지갑 생성
     */
    public Wallet createWallet(CashMember member) {
        Wallet wallet = new Wallet(member);

        return walletRepository.save(wallet);
    }
}