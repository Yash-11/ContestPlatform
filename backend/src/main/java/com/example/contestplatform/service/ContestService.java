package com.example.contestplatform.service;

import java.time.LocalDateTime;
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

    @Autowired
    private ProblemService problemService;

    // Create a new contest
    public Contest createContest(Contest contest) {
        Contest savedContest = contestRepository.save(contest);
        Long contestNo = savedContest.getId()+100;
        savedContest.setName(savedContest.getName()+contestNo);
        System.out.println("Create contest: name: "+savedContest.getName());
        return contestRepository.save(savedContest);
    }

    public List<Contest> getPastContests() {
        List<Contest> currentContests = contestRepository.findByEndTimeBefore(LocalDateTime.now());
        if (currentContests.isEmpty()) {
            List<Problem> problems = problemService.getProblems();
            Contest contest = new Contest();
            contest.setName("Div2 ");
            contest.setProblems(problems.subList(0, Math.min(3, problems.size())));
            contest.setStartTime(LocalDateTime.now().minusDays(2));
            contest.setEndTime(LocalDateTime.now().minusDays(1));
            createContest(contest);
        }
        return contestRepository.findByEndTimeBefore(LocalDateTime.now());
    }

    public List<Contest> getCurrentContests() {
        List<Contest> currentContests = contestRepository.findByEndTimeAfter(LocalDateTime.now());
        if (currentContests.isEmpty()) {
            List<Problem> problems = problemService.getProblems();
            Contest contest = new Contest();
            contest.setName("Div2 ");
            contest.setProblems(problems.subList(0, Math.min(3, problems.size())));
            contest.setStartTime(LocalDateTime.now());
            contest.setEndTime(LocalDateTime.now().plusDays(1));
            createContest(contest);
        }
        return contestRepository.findByEndTimeAfter(LocalDateTime.now());
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
