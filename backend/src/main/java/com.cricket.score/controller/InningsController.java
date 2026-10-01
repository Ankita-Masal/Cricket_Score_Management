package com.cricket.score.controller;

import com.cricket.score.entity.Innings;
import com.cricket.score.service.InningsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/innings")
@CrossOrigin(origins = "http://localhost:5173")
public class InningsController {

    private final InningsService inningsService;

    public InningsController(InningsService inningsService) {
        this.inningsService = inningsService;
    }

    @PostMapping
    public ResponseEntity<Innings> createInnings(@RequestBody Innings innings) {
        return ResponseEntity.ok(inningsService.createInnings(innings));
    }

    @GetMapping
    public ResponseEntity<List<Innings>> getAllInnings() {
        return ResponseEntity.ok(inningsService.getAllInnings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Innings> getInningsById(@PathVariable Long id) {
        return inningsService.getInningsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<Innings>> getInningsByMatch(
            @PathVariable Long matchId) {

        return ResponseEntity.ok(
                inningsService.getInningsByMatch(matchId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Innings> updateInnings(
            @PathVariable Long id,
            @RequestBody Innings innings) {

        return ResponseEntity.ok(
                inningsService.updateInnings(id, innings)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInnings(@PathVariable Long id) {
        inningsService.deleteInnings(id);
        return ResponseEntity.noContent().build();
    }
}