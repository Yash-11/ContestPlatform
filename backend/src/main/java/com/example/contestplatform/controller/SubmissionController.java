package com.example.contestplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.contestplatform.kafka.SubmissionProducer;
import com.example.contestplatform.model.Submission;
import com.example.contestplatform.repository.SubmissionRepository;
import com.example.contestplatform.service.JudgingService;

@RestController
@RequestMapping("/submissions")
@CrossOrigin(origins = "http://localhost:3000")
public class SubmissionController {

    @Autowired
    private SubmissionProducer submissionProducer;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private JudgingService judgingService;

    @PostMapping("/submit")
    public ResponseEntity<Submission> submitCode(@RequestBody Submission submission) {
        // Save initial submission state
        submission.setStatus("PENDING");
        submissionRepository.save(submission);
        
        // Produce submission event to Kafka
        // String submissionEvent ="";//= serializeSubmissionToJSON(submission);  // Write method to serialize
        // submissionProducer.sendSubmission(submissionEvent);

        judgingService.submitCode(submission);

        return ResponseEntity.ok(submission);
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<Submission> getSubmissionResult(@PathVariable Long id) {
        System.out.println(id);
        Submission submission = submissionRepository.findById(id).orElse(null);
        return ResponseEntity.ok(submission);
    }
}