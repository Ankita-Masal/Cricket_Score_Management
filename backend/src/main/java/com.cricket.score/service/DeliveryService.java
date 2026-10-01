package com.cricket.score.service;

import com.cricket.score.entity.Delivery;
import com.cricket.score.entity.DeliveryType;
import com.cricket.score.entity.Innings;
import com.cricket.score.entity.InningsStatus;
import com.cricket.score.entity.Match;
import com.cricket.score.entity.MatchStatus;
import com.cricket.score.repository.DeliveryRepository;
import com.cricket.score.repository.InningsRepository;
import com.cricket.score.repository.MatchRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final InningsRepository inningsRepository;
    private final MatchRepository matchRepository;

    public DeliveryService(
            DeliveryRepository deliveryRepository,
            InningsRepository inningsRepository,
            MatchRepository matchRepository) {

        this.deliveryRepository = deliveryRepository;
        this.inningsRepository = inningsRepository;
        this.matchRepository = matchRepository;
    }

    public Delivery createDelivery(Delivery delivery) {

        Delivery savedDelivery = deliveryRepository.save(delivery);

        recalculateInnings(delivery.getInnings().getId());

        return savedDelivery;
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Optional<Delivery> getDeliveryById(Long id) {
        return deliveryRepository.findById(id);
    }

    public List<Delivery> getDeliveriesByInnings(Long inningsId) {
        return deliveryRepository.findByInningsId(inningsId);
    }

    public Delivery updateDelivery(Long id, Delivery updatedDelivery) {

        Delivery existingDelivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        Long oldInningsId = existingDelivery.getInnings().getId();

        existingDelivery.setInnings(updatedDelivery.getInnings());
        existingDelivery.setOverNumber(updatedDelivery.getOverNumber());
        existingDelivery.setBallNumber(updatedDelivery.getBallNumber());
        existingDelivery.setBatsman(updatedDelivery.getBatsman());
        existingDelivery.setBowler(updatedDelivery.getBowler());
        existingDelivery.setRuns(updatedDelivery.getRuns());
        existingDelivery.setExtras(updatedDelivery.getExtras());
        existingDelivery.setWicket(updatedDelivery.getWicket());
        existingDelivery.setDeliveryType(updatedDelivery.getDeliveryType());

        Delivery savedDelivery = deliveryRepository.save(existingDelivery);

        recalculateInnings(oldInningsId);

        if (!oldInningsId.equals(updatedDelivery.getInnings().getId())) {
            recalculateInnings(updatedDelivery.getInnings().getId());
        }

        return savedDelivery;
    }

    public void deleteDelivery(Long id) {

        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        Long inningsId = delivery.getInnings().getId();

        deliveryRepository.deleteById(id);

        recalculateInnings(inningsId);
    }

    private void recalculateInnings(Long inningsId) {

        Innings innings = inningsRepository.findById(inningsId)
                .orElseThrow(() -> new RuntimeException("Innings not found"));

        List<Delivery> deliveries =
                deliveryRepository.findByInningsId(inningsId);

        int totalRuns = 0;
        int wickets = 0;
        int totalBalls = 0;

        for (Delivery delivery : deliveries) {

            // Bat runs + extras
            totalRuns += delivery.getRuns() + delivery.getExtras();

            // Count wickets
            if (Boolean.TRUE.equals(delivery.getWicket())) {
                wickets++;
            }

            // Count only legal deliveries
            if (delivery.getDeliveryType() == DeliveryType.NORMAL
                    || delivery.getDeliveryType() == DeliveryType.BYE
                    || delivery.getDeliveryType() == DeliveryType.LEG_BYE) {

                totalBalls++;
            }
        }

        innings.setTotalRuns(totalRuns);
        innings.setWickets(wickets);
        innings.setTotalBalls(totalBalls);

        // Update innings status
        if (deliveries.isEmpty()) {
            innings.setStatus(InningsStatus.NOT_STARTED);
        } else {
            innings.setStatus(InningsStatus.IN_PROGRESS);
        }

        inningsRepository.save(innings);

        // Change match status to LIVE when scoring starts
        if (!deliveries.isEmpty()) {

            Match match = innings.getMatch();

            if (match.getStatus() == MatchStatus.SCHEDULED) {
                match.setStatus(MatchStatus.LIVE);
                matchRepository.save(match);
            }
        }
    }
}