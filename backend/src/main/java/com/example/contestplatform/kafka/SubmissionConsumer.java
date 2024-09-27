package com.example.contestplatform.kafka;

import org.springframework.stereotype.Service;

import com.example.contestplatform.model.Submission;
import com.example.contestplatform.repository.SubmissionRepository;
import com.example.contestplatform.service.JudgingWorker;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SubmissionConsumer {

    @Autowired
    private JudgingWorker judgingWorker;

    @KafkaListener(topics = "submissions", groupId = "contest-submissions")
    public void consumeSubmission(Submission submissionEvent) {
        // Deserialize submissionEvent JSON (using Jackson or similar)
        // Process code submission using Docker, compile and run
        // Update Submission entity status in DB based on results

        // Submission submission = (Submission) submissionEvent; 
        Submission submission = submissionEvent; 
        judgingWorker.compileAndRun(submission);
    }
}

