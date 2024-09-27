package com.example.contestplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.contestplatform.kafka.SubmissionProducer;
import com.example.contestplatform.model.Submission;

@Service
public class JudgingService {

    @Autowired
    private JudgingWorker judgingWorker;

    @Autowired
    private SubmissionProducer submissionProducer;
    
    public void submitCode(Submission submission) {
        submissionProducer.sendSubmission(submission);
        // judgingWorker.compileAndRun(submission);
    }
}
