// File: backend/src/main/java/com/example/deliveryapp/service/WalletService.java
package org.samaan.services;

import org.samaan.model.Wallet;
import org.samaan.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet createWallet(String userId, String currency) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(0.0);
        wallet.setCurrency(currency);
        wallet.setCreatedAt(LocalDateTime.now().toString());
        wallet.setUpdatedAt(LocalDateTime.now().toString());
        return walletRepository.save(wallet);
    }

    public Wallet getWalletByUserId(String userId) {
        return walletRepository.findByUserId(userId);
    }

    public Wallet updateBalance(String userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet != null) {
            wallet.setBalance(wallet.getBalance() + amount);
            wallet.setUpdatedAt(LocalDateTime.now().toString());
            return walletRepository.save(wallet);
        }
        return null;
    }
}