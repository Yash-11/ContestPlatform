package com.example.contestplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.contestplatform.service.RedisLeaderboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private RedisLeaderboardService leaderboardService;

    /**
     * Endpoint to add a user's score to the leaderboard.
     * @param userId User ID.
     * @param score Score to be added.
     * @return Response message.
     */
    @PostMapping("/addScore")
    public ResponseEntity<String> addScore(@RequestParam String userId, @RequestParam double score) {
        leaderboardService.addScore(userId, score);
        return ResponseEntity.ok("Score added!");
    }

    /**
     * Get top N users from the leaderboard.
     * @param topN Number of top users to retrieve.
     * @return List of users with their ranks and scores.
     */
    // @GetMapping("/top/{topN}")
    // public ResponseEntity<Set<ZSetOperations.TypedTuple<String>>> getTopUsers(@PathVariable int topN) {
    //     return ResponseEntity.ok(leaderboardService.getTopUsers(topN));
    // }

    /**
     * Get a user's rank.
     * @param userId User ID.
     * @return The rank of the user.
     */
    @GetMapping("/rank/{userId}")
    public ResponseEntity<Long> getUserRank(@PathVariable String userId) {
        Long rank = leaderboardService.getUserRank(userId);
        return rank != null ? ResponseEntity.ok(rank) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Get a user's score.
     * @param userId User ID.
     * @return The score of the user.
     */
    @GetMapping("/score/{userId}")
    public ResponseEntity<Double> getUserScore(@PathVariable String userId) {
        Double score = leaderboardService.getUserScore(userId);
        return score != null ? ResponseEntity.ok(score) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

