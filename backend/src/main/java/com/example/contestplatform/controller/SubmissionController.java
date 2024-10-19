package com.example.contestplatform.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.contestplatform.dto.SubmissionDTO;
import com.example.contestplatform.kafka.SubmissionProducer;
import com.example.contestplatform.model.Submission;
import com.example.contestplatform.repository.SubmissionRepository;
import com.example.contestplatform.service.ContestantService;
import com.example.contestplatform.service.CustomUserDetailsService;
import com.example.contestplatform.service.JudgingService;
import com.example.contestplatform.service.SubmissionService;

import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private JudgingService judgingService;
    @Autowired
    private ContestantService contestantService;

    @PostMapping("/submit")
    public ResponseEntity<Submission> submitCode(@RequestBody Submission submission, Authentication authentication) {

        String username = authentication.getName();
        Long userid = contestantService.findOneByUsernameIgnoreCase(username).get().getId();

        // Save initial submission state
        submission.setStatus("PENDING");
        submission.setUserId(userid);
        submissionService.save(submission);
        
        // Produce submission event to Kafka
        // String submissionEvent ="";//= serializeSubmissionToJSON(submission);  // Write method to serialize
        // submissionProducer.sendSubmission(submissionEvent);

        judgingService.submitCode(submission);
        return ResponseEntity.ok(submission);
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionResult(@PathVariable Long id) {
        Submission submission = submissionService.getReferenceById(id);
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setCodeOutput(submission.getCodeOutput());
        submissionDTO.setStatus(submission.getStatus());
        return ResponseEntity.ok(submissionDTO);
    }

    @GetMapping
    public List<Submission> getSubmissions(@RequestParam Long problemId, Authentication authentication) {

        String username = authentication.getName();
        Long userId = contestantService.findOneByUsernameIgnoreCase(username).get().getId();
        return submissionService.getSubmissionsByUserIdAndProblemId(userId, problemId);
    }
}