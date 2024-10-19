package com.example.contestplatform.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contestplatform.model.Submission;
import com.example.contestplatform.repository.SubmissionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public Submission save(Submission submission) {
        log.info("Saving submission: "+submission.getId()+" Status: "+submission.getStatus());
        return submissionRepository.save(submission);
    }

    public Submission getReferenceById(Long id) {
        return submissionRepository.getReferenceById(id);
    }

    public List<Submission> getSubmissionsByUserIdAndProblemId(Long userId, Long problemId) {
        return submissionRepository.findByUserIdAndProblemId(userId, problemId);
    }

    
}
