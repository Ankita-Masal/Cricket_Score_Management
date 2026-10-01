package com.cricket.score.service;

import com.cricket.score.entity.Innings;
import com.cricket.score.entity.Match;
import com.cricket.score.repository.InningsRepository;
import com.cricket.score.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatchSummaryService {

    private final MatchRepository matchRepository;
    private final InningsRepository inningsRepository;

    public MatchSummaryService(
            MatchRepository matchRepository,
            InningsRepository inningsRepository) {

        this.matchRepository = matchRepository;
        this.inningsRepository = inningsRepository;
    }

    public Map<String, Object> getMatchSummary(Long matchId) {

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        List<Innings> inningsList =
                inningsRepository.findByMatchId(matchId);

        Map<String, Object> summary = new HashMap<>();

        // Match information
        summary.put("matchId", match.getId());
        summary.put("team1", match.getTeam1().getName());
        summary.put("team2", match.getTeam2().getName());
        summary.put("venue", match.getVenue());
        summary.put("matchDate", match.getMatchDate());
        summary.put("matchType", match.getMatchType());
        summary.put("status", match.getStatus());

        // Innings information
        List<Map<String, Object>> inningsSummary = new ArrayList<>();

        for (Innings innings : inningsList) {

            Map<String, Object> inningsData = new HashMap<>();

            inningsData.put(
                    "inningsId",
                    innings.getId()
            );

            inningsData.put(
                    "inningsNumber",
                    innings.getInningsNumber()
            );

            inningsData.put(
                    "battingTeam",
                    innings.getBattingTeam().getName()
            );

            inningsData.put(
                    "bowlingTeam",
                    innings.getBowlingTeam().getName()
            );

            inningsData.put(
                    "runs",
                    innings.getTotalRuns()
            );

            inningsData.put(
                    "wickets",
                    innings.getWickets()
            );

            inningsData.put(
                    "overs",
                    formatOvers(innings.getTotalBalls())
            );

            inningsData.put(
                    "status",
                    innings.getStatus()
            );

            inningsSummary.add(inningsData);
        }

        summary.put("innings", inningsSummary);

        return summary;
    }

    private String formatOvers(int totalBalls) {

        int overs = totalBalls / 6;
        int balls = totalBalls % 6;

        return overs + "." + balls;
    }
}