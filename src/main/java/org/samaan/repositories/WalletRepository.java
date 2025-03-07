package org.samaan.repositories;

import org.samaan.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WalletRepository extends MongoRepository<Wallet, String> {
    Wallet findByUserId(String userId);

    Wallet save(Wallet wallet);
}