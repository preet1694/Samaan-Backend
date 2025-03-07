package org.samaan.repositories;

import org.samaan.model.Sender;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SenderRepository extends MongoRepository<Sender, String> {
    Sender findByUserId(String userId);

    Sender save(Sender sender);
}