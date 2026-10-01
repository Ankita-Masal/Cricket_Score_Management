package com.cricket.score.service;

import com.cricket.score.entity.Delivery;
import com.cricket.score.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final DeliveryRepository deliveryRepository;

    public StatisticsService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Map<String, Object> getBattingStatistics(
            Long inningsId,
            Long playerId) {

        List<Delivery> deliveries =
                deliveryRepository.findByInningsId(inningsId);

        int runs = 0;
        int balls = 0;
        int fours = 0;
        int sixes = 0;

        for (Delivery delivery : deliveries) {

            if (delivery.getBatsman().getId().equals(playerId)) {

                runs += delivery.getRuns();

                // Wide does not count as a ball faced
                if (!delivery.getDeliveryType().name().equals("WIDE")) {
                    balls++;
                }

                if (delivery.getRuns() == 4) {
                    fours++;
                }

                if (delivery.getRuns() == 6) {
                    sixes++;
                }
            }
        }

        double strikeRate = 0;

        if (balls > 0) {
            strikeRate = ((double) runs / balls) * 100;
        }

        Map<String, Object> statistics = new HashMap<>();

        statistics.put("playerId", playerId);
        statistics.put("runs", runs);
        statistics.put("balls", balls);
        statistics.put("fours", fours);
        statistics.put("sixes", sixes);
        statistics.put("strikeRate", Math.round(strikeRate * 100.0) / 100.0);

        return statistics;
    }

    public Map<String, Object> getBowlingStatistics(
            Long inningsId,
            Long playerId) {

        List<Delivery> deliveries =
                deliveryRepository.findByInningsId(inningsId);

        int legalBalls = 0;
        int runsConceded = 0;
        int wickets = 0;

        for (Delivery delivery : deliveries) {

            if (delivery.getBowler().getId().equals(playerId)) {

                // Bowler is charged with batsman runs
                // and wides/no-balls.
                runsConceded += delivery.getRuns();

                if (delivery.getDeliveryType().name().equals("WIDE")
                        || delivery.getDeliveryType().name().equals("NO_BALL")) {

                    runsConceded += delivery.getExtras();
                }

                if (!delivery.getDeliveryType().name().equals("WIDE")
                        && !delivery.getDeliveryType().name().equals("NO_BALL")) {

                    legalBalls++;
                }

                if (Boolean.TRUE.equals(delivery.getWicket())) {
                    wickets++;
                }
            }
        }

        int overs = legalBalls / 6;
        int balls = legalBalls % 6;

        double economy = 0;

        if (legalBalls > 0) {
            economy = ((double) runsConceded / legalBalls) * 6;
        }

        Map<String, Object> statistics = new HashMap<>();

        statistics.put("playerId", playerId);
        statistics.put("overs", overs + "." + balls);
        statistics.put("legalBalls", legalBalls);
        statistics.put("runsConceded", runsConceded);
        statistics.put("wickets", wickets);
        statistics.put("economy", Math.round(economy * 100.0) / 100.0);

        return statistics;
    }
}