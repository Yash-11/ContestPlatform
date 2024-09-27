package com.example.contestplatform.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.contestplatform.model.Submission;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SubmissionProducer {
    @Autowired
    private KafkaTemplate<String, Submission> kafkaTemplate;

    public void sendSubmission(Submission submissionEvent) {
        kafkaTemplate.send("submissions", submissionEvent);
    }
}
