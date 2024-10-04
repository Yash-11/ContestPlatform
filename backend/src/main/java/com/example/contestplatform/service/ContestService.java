package com.example.contestplatform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contestplatform.model.Contest;
import com.example.contestplatform.model.Problem;
import com.example.contestplatform.repository.ContestRepository;

@Service
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    // Create a new contest
    public Contest createContest(Contest contest) {
        System.out.println("Create contest: name: "+contest.getName());
        return contestRepository.save(contest);
    }

    // Get a list of all contests
    public List<Contest> getAllContests() {
        return contestRepository.findAll();
    }

    // Fetch a specific contest by ID
    public Optional<Contest> getContestById(Long id) {
        return contestRepository.findById(id);
    }

    public Optional<Contest> findOneByNameIgnoreCase(String title) {        
        return contestRepository.findOneByNameIgnoreCase(title);
    }

    // Update contest details
    public Contest updateContest(Long id, Contest updatedContest) {
        Optional<Contest> existingContest = contestRepository.findById(id);
        if (existingContest.isPresent()) {
            Contest contest = existingContest.get();
            contest.setName(updatedContest.getName());
            contest.setDescription(updatedContest.getDescription());
            contest.setStartTime(updatedContest.getStartTime());
            contest.setEndTime(updatedContest.getEndTime());
            contest.setProblems(updatedContest.getProblems());
            return contestRepository.save(contest);
        } else {
            throw new RuntimeException("Contest not found");
        }
    }

    // Delete a contest
    public void deleteContest(Long id) {
        contestRepository.deleteById(id);
    }
}
