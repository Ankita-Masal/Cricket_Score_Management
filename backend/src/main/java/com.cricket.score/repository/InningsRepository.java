package com.cricket.score.repository;

import com.cricket.score.entity.Innings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InningsRepository extends JpaRepository<Innings, Long> {

    List<Innings> findByMatchId(Long matchId);
}