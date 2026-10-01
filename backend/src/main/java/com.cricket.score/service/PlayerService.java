package com.cricket.score.service;

import com.cricket.score.entity.Player;
import com.cricket.score.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public List<Player> getPlayersByTeam(Long teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    public Player updatePlayer(Long id, Player updatedPlayer) {

        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        existingPlayer.setName(updatedPlayer.getName());
        existingPlayer.setRole(updatedPlayer.getRole());
        existingPlayer.setBattingStyle(updatedPlayer.getBattingStyle());
        existingPlayer.setBowlingStyle(updatedPlayer.getBowlingStyle());
        existingPlayer.setJerseyNumber(updatedPlayer.getJerseyNumber());
        existingPlayer.setTeam(updatedPlayer.getTeam());

        return playerRepository.save(existingPlayer);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}