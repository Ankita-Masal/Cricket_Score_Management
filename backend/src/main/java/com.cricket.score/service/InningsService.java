package com.cricket.score.service;

import com.cricket.score.entity.Innings;
import com.cricket.score.repository.InningsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InningsService {

    private final InningsRepository inningsRepository;

    public InningsService(InningsRepository inningsRepository) {
        this.inningsRepository = inningsRepository;
    }

    public Innings createInnings(Innings innings) {
        return inningsRepository.save(innings);
    }

    public List<Innings> getAllInnings() {
        return inningsRepository.findAll();
    }

    public Optional<Innings> getInningsById(Long id) {
        return inningsRepository.findById(id);
    }

    public List<Innings> getInningsByMatch(Long matchId) {
        return inningsRepository.findByMatchId(matchId);
    }

    public Innings updateInnings(Long id, Innings updatedInnings) {

        Innings existingInnings = inningsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innings not found"));

        existingInnings.setMatch(updatedInnings.getMatch());
        existingInnings.setBattingTeam(updatedInnings.getBattingTeam());
        existingInnings.setBowlingTeam(updatedInnings.getBowlingTeam());
        existingInnings.setInningsNumber(updatedInnings.getInningsNumber());
        existingInnings.setTotalRuns(updatedInnings.getTotalRuns());
        existingInnings.setWickets(updatedInnings.getWickets());
        existingInnings.setTotalBalls(updatedInnings.getTotalBalls());
        existingInnings.setStatus(updatedInnings.getStatus());

        return inningsRepository.save(existingInnings);
    }

    public void deleteInnings(Long id) {
        inningsRepository.deleteById(id);
    }
}