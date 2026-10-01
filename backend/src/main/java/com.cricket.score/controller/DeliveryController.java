package com.cricket.score.controller;

import com.cricket.score.entity.Delivery;
import com.cricket.score.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@CrossOrigin(origins = "http://localhost:5173")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(
            @RequestBody Delivery delivery) {

        return ResponseEntity.ok(
                deliveryService.createDelivery(delivery)
        );
    }

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {

        return ResponseEntity.ok(
                deliveryService.getAllDeliveries()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(
            @PathVariable Long id) {

        return deliveryService.getDeliveryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/innings/{inningsId}")
    public ResponseEntity<List<Delivery>> getDeliveriesByInnings(
            @PathVariable Long inningsId) {

        return ResponseEntity.ok(
                deliveryService.getDeliveriesByInnings(inningsId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Delivery> updateDelivery(
            @PathVariable Long id,
            @RequestBody Delivery delivery) {

        return ResponseEntity.ok(
                deliveryService.updateDelivery(id, delivery)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(
            @PathVariable Long id) {

        deliveryService.deleteDelivery(id);

        return ResponseEntity.noContent().build();
    }
}