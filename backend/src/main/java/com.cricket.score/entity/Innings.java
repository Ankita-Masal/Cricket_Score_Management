package com.cricket.score.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "innings")
public class Innings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "batting_team_id", nullable = false)
    private Team battingTeam;

    @ManyToOne
    @JoinColumn(name = "bowling_team_id", nullable = false)
    private Team bowlingTeam;

    @Column(nullable = false)
    private Integer inningsNumber;

    @Column(nullable = false)
    private Integer totalRuns = 0;

    @Column(nullable = false)
    private Integer wickets = 0;

    @Column(nullable = false)
    private Integer totalBalls = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InningsStatus status;

    public Innings() {
    }

    public Innings(
            Match match,
            Team battingTeam,
            Team bowlingTeam,
            Integer inningsNumber,
            Integer totalRuns,
            Integer wickets,
            Integer totalBalls,
            InningsStatus status) {

        this.match = match;
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.inningsNumber = inningsNumber;
        this.totalRuns = totalRuns;
        this.wickets = wickets;
        this.totalBalls = totalBalls;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(Team battingTeam) {
        this.battingTeam = battingTeam;
    }

    public Team getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(Team bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    public Integer getInningsNumber() {
        return inningsNumber;
    }

    public void setInningsNumber(Integer inningsNumber) {
        this.inningsNumber = inningsNumber;
    }

    public Integer getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(Integer totalRuns) {
        this.totalRuns = totalRuns;
    }

    public Integer getWickets() {
        return wickets;
    }

    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }

    public Integer getTotalBalls() {
        return totalBalls;
    }

    public void setTotalBalls(Integer totalBalls) {
        this.totalBalls = totalBalls;
    }

    public InningsStatus getStatus() {
        return status;
    }

    public void setStatus(InningsStatus status) {
        this.status = status;
    }
}