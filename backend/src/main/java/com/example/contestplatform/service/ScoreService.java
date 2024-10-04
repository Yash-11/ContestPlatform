package com.example.contestplatform.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contestplatform.model.Contest;
import com.example.contestplatform.model.Contestant;
import com.example.contestplatform.model.Problem;
import com.example.contestplatform.model.Score;
import com.example.contestplatform.repository.ScoreRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScoreService {
    
    @Autowired
    private ScoreRepository scoreRepository;

    public Score save(Score score) {
        log.info("saving score: "+score.getId());
        return scoreRepository.save(score);
    }

    public Score findOrCreateScore(Contest contest, Contestant contestant) {
        // Try to find the score
        Score score = scoreRepository.findByContestAndContestant(contest, contestant);

        // If score not found, create a new one
        if (score == null) {
            score = new Score();
            score.setContest(contest);
            score.setContestant(contestant);
            score.setRank(0);  // You can set default values if needed
            score.setScore(0);  // Initial score value
            score.setSubmissions(new ArrayList<>());  // Initialize with an empty submission list

            // Save the new score to the database
            score = scoreRepository.save(score);
        }

        return score;
    }
}
