// File: backend/src/main/java/com/example/deliveryapp/service/CarrierService.java
package org.samaan.services;

import org.samaan.model.Carrier;
import org.samaan.repositories.CarrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarrierService {

    @Autowired
    private CarrierRepository carrierRepository;

    public Carrier createCarrier(Carrier carrier) {
        carrier.setCreatedAt(LocalDateTime.now().toString());
        carrier.setUpdatedAt(LocalDateTime.now().toString());
        return carrierRepository.save(carrier);
    }

    public Carrier updateCarrier(Carrier carrier) {
        carrier.setUpdatedAt(LocalDateTime.now().toString());
        return carrierRepository.save(carrier);
    }

    public Carrier getCarrierByUserId(String userId) {
        return carrierRepository.findByUserId(userId);
    }

    public List<Carrier> getAvailableCarriers() {
        return carrierRepository.findByIsAvailableTrue();
    }
}