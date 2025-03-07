// File: backend/src/main/java/com/example/deliveryapp/controller/CarrierController.java
package org.samaan.controllers;

import org.samaan.model.Carrier;
import org.samaan.services.CarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/carriers")
public class CarrierController {

    @Autowired
    private CarrierService carrierService;

    @PostMapping
    public ResponseEntity<?> createCarrier(@RequestBody Carrier carrier) {
        Carrier createdCarrier = carrierService.createCarrier(carrier);
        return ResponseEntity.ok(createdCarrier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarrier(@PathVariable String id, @RequestBody Carrier carrier) {
        carrier.setId(id);
        Carrier updatedCarrier = carrierService.updateCarrier(carrier);
        return ResponseEntity.ok(updatedCarrier);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCarrierByUserId(@PathVariable String userId) {
        Carrier carrier = carrierService.getCarrierByUserId(userId);
        return ResponseEntity.ok(carrier);
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableCarriers() {
        List<Carrier> availableCarriers = carrierService.getAvailableCarriers();
        return ResponseEntity.ok(availableCarriers);
    }
}