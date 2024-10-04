package com.example.contestplatform.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Contest contest;

    @ManyToOne
    private Contestant contestant;

    @OneToMany
    private List<Submission> submissions;

    private int rank;      // Contestant's rank in the contest
    private int score;     // Contestant's score in the contest

    // Getters and Setters

    public boolean isProblemSolved(Long problemId) {
        return submissions.stream()
                .anyMatch(submission -> submission.getProblemId().equals(problemId) && 
                                        submission.getStatus().equals("SUCCESS"));
    }

    // Method to update the score based on new submission
    public void updateScore(Submission submission) {
        if ("SUCCESS".equals(submission.getStatus())) {
            if (!isProblemSolved(submission.getProblemId())) {
                // Assuming each successful submission adds a certain score
                int points = 10; // Set points awarded for a successful submission
                this.score += points; // Increase total score
                submissions.add(submission); // Add submission to the score
            }
        }
    }
}

