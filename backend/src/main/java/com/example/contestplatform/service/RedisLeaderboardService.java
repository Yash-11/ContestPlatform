package com.example.contestplatform.service;

import org.springframework.stereotype.Service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

@Service
public class RedisLeaderboardService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String LEADERBOARD_KEY = "contest_leaderboard";

    /**
     * Add a user's score to the leaderboard.
     * @param userId Unique user identifier.
     * @param score The score to add.
     */
    public void addScore(String userId, double score) {
        redisTemplate.opsForZSet().incrementScore(LEADERBOARD_KEY, userId, score);
    }

    /**
     * Get the top users in the leaderboard.
     * @param topN Number of top users to retrieve.
     * @return List of userId and score.
    //  */
    // public Set<ZSetOperations.TypedTuple<String>> getTopUsers(int topN) {
    //     return redisTemplate.opsForZSet().reverseRangeWithScores(LEADERBOARD_KEY, 0, topN - 1);
    // }

    /**
     * Get the rank and score of a specific user.
     * @param userId Unique user identifier.
     * @return The rank of the user (0-based index) and their score.
     */
    public Long getUserRank(String userId) {
        return redisTemplate.opsForZSet().reverseRank(LEADERBOARD_KEY, userId);
    }

    public Double getUserScore(String userId) {
        return redisTemplate.opsForZSet().score(LEADERBOARD_KEY, userId);
    }
}

