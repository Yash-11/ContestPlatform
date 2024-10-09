package com.example.contestplatform.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.contestplatform.model.Contest;
import com.example.contestplatform.service.ContestService;

@RestController
@RequestMapping("/api/contests")
public class ContestController {

    @Autowired
    private ContestService contestService;

    // Create a new contest
    @PostMapping
    public ResponseEntity<Contest> createContest(@RequestBody Contest contest) {
        Contest createdContest = contestService.createContest(contest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContest);
    }

    // Get all contests
    @GetMapping
    public ResponseEntity<List<Contest>> getAllContests() {
        List<Contest> contests = contestService.getAllContests();
        return ResponseEntity.ok(contests);
    }

    @GetMapping("/current")
    public List<Contest> getCurrentContests() {
        return contestService.getCurrentContests();
    }

    // Endpoint for past contests
    @GetMapping("/past")
    public List<Contest> getPastContests() {
        return contestService.getPastContests();
    }

    // Get a contest by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contest> getContestById(@PathVariable Long id) {
        Optional<Contest> contest = contestService.getContestById(id);
        return contest.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Update a contest
    @PutMapping("/{id}")
    public ResponseEntity<Contest> updateContest(@PathVariable Long id, @RequestBody Contest contest) {
        Contest updatedContest = contestService.updateContest(id, contest);
        return ResponseEntity.ok(updatedContest);
    }

    // Delete a contest
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContest(@PathVariable Long id) {
        contestService.deleteContest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

