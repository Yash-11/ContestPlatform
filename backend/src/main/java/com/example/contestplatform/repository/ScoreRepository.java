package com.example.contestplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.contestplatform.model.Contest;
import com.example.contestplatform.model.Contestant;
import com.example.contestplatform.model.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByContestOrderByRankAsc(Contest contest);
    Score findByContestAndContestant(Contest contest, Contestant contestant);
}