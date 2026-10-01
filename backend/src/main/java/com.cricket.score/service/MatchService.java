package com.cricket.score.service;

import com.cricket.score.entity.Match;
import com.cricket.score.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Match updateMatch(Long id, Match updatedMatch) {

        Match existingMatch = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        existingMatch.setTeam1(updatedMatch.getTeam1());
        existingMatch.setTeam2(updatedMatch.getTeam2());
        existingMatch.setVenue(updatedMatch.getVenue());
        existingMatch.setMatchDate(updatedMatch.getMatchDate());
        existingMatch.setMatchType(updatedMatch.getMatchType());
        existingMatch.setStatus(updatedMatch.getStatus());

        return matchRepository.save(existingMatch);
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }
}