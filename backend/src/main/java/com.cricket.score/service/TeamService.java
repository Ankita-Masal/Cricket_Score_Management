package com.cricket.score.service;

import com.cricket.score.entity.Team;
import com.cricket.score.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    public Team updateTeam(Long id, Team updatedTeam) {

        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        existingTeam.setName(updatedTeam.getName());
        existingTeam.setCountry(updatedTeam.getCountry());
        existingTeam.setShortName(updatedTeam.getShortName());
        existingTeam.setLogoUrl(updatedTeam.getLogoUrl());

        return teamRepository.save(existingTeam);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}