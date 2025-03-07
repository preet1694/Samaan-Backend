package org.samaan.repositories;

import org.samaan.model.Carrier;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CarrierRepository extends MongoRepository<Carrier, String> {
    Carrier findByUserId(String userId);
    List<Carrier> findByIsAvailableTrue();

    Carrier save(Carrier carrier);
}