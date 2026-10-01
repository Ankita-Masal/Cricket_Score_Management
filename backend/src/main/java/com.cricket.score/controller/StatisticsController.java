package com.cricket.score.controller;

import com.cricket.score.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "http://localhost:5173")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/batting/{inningsId}/{playerId}")
    public ResponseEntity<Map<String, Object>> getBattingStatistics(
            @PathVariable Long inningsId,
            @PathVariable Long playerId) {

        return ResponseEntity.ok(
                statisticsService.getBattingStatistics(
                        inningsId,
                        playerId
                )
        );
    }

    @GetMapping("/bowling/{inningsId}/{playerId}")
    public ResponseEntity<Map<String, Object>> getBowlingStatistics(
            @PathVariable Long inningsId,
            @PathVariable Long playerId) {

        return ResponseEntity.ok(
                statisticsService.getBowlingStatistics(
                        inningsId,
                        playerId
                )
        );
    }
}