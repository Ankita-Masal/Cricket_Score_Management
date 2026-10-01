package com.cricket.score.controller;

import com.cricket.score.service.MatchSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/match-summary")
@CrossOrigin(origins = "http://localhost:5173")
public class MatchSummaryController {

    private final MatchSummaryService matchSummaryService;

    public MatchSummaryController(
            MatchSummaryService matchSummaryService) {

        this.matchSummaryService = matchSummaryService;
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Map<String, Object>> getMatchSummary(
            @PathVariable Long matchId) {

        return ResponseEntity.ok(
                matchSummaryService.getMatchSummary(matchId)
        );
    }
}