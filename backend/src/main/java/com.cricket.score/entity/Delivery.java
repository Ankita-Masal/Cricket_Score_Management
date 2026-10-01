package com.cricket.score.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "innings_id", nullable = false)
    private Innings innings;

    @Column(nullable = false)
    private Integer overNumber;

    @Column(nullable = false)
    private Integer ballNumber;

    @ManyToOne
    @JoinColumn(name = "batsman_id", nullable = false)
    private Player batsman;

    @ManyToOne
    @JoinColumn(name = "bowler_id", nullable = false)
    private Player bowler;

    @Column(nullable = false)
    private Integer runs = 0;

    @Column(nullable = false)
    private Integer extras = 0;

    @Column(nullable = false)
    private Boolean wicket = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    public Delivery() {
    }

    public Delivery(
            Innings innings,
            Integer overNumber,
            Integer ballNumber,
            Player batsman,
            Player bowler,
            Integer runs,
            Integer extras,
            Boolean wicket,
            DeliveryType deliveryType) {

        this.innings = innings;
        this.overNumber = overNumber;
        this.ballNumber = ballNumber;
        this.batsman = batsman;
        this.bowler = bowler;
        this.runs = runs;
        this.extras = extras;
        this.wicket = wicket;
        this.deliveryType = deliveryType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Innings getInnings() {
        return innings;
    }

    public void setInnings(Innings innings) {
        this.innings = innings;
    }

    public Integer getOverNumber() {
        return overNumber;
    }

    public void setOverNumber(Integer overNumber) {
        this.overNumber = overNumber;
    }

    public Integer getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(Integer ballNumber) {
        this.ballNumber = ballNumber;
    }

    public Player getBatsman() {
        return batsman;
    }

    public void setBatsman(Player batsman) {
        this.batsman = batsman;
    }

    public Player getBowler() {
        return bowler;
    }

    public void setBowler(Player bowler) {
        this.bowler = bowler;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getExtras() {
        return extras;
    }

    public void setExtras(Integer extras) {
        this.extras = extras;
    }

    public Boolean getWicket() {
        return wicket;
    }

    public void setWicket(Boolean wicket) {
        this.wicket = wicket;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }
}