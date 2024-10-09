package com.example.contestplatform.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.contestplatform.model.Contest;
import com.example.contestplatform.model.Contestant;
import com.example.contestplatform.model.Problem;
import com.example.contestplatform.model.Submission;
import com.example.contestplatform.repository.SubmissionRepository;
import com.example.contestplatform.service.ContestService;
import com.example.contestplatform.service.ContestantService;
import com.example.contestplatform.service.ProblemService;
import com.example.contestplatform.service.SubmissionService;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ContestantService contestantService;

    @Autowired
    private ContestService contestService;

    private void createContestantIfNotExists(String username) {
        if (contestantService.findOneByUsernameIgnoreCase(username).isEmpty()) {
            Contestant contestant = new Contestant();
            contestant.setUsername(username);
            contestant.setPassword("pass");
            contestantService.save(contestant);
        }
    }

    
    private Problem createProblemIfNotExists(String title, String desc, String input, String output) {
        Optional<Problem> optionalProblem = problemService.findOneByTitleIgnoreCase(title);
        if (!optionalProblem.isPresent()) {
            Problem problem = new Problem();
            problem.setTitle(title);
            problem.setDescription(desc);
            problem.setInputFormat(input);
            problem.setOutputFormat(output);
            return problemService.save(problem);
        }
        return optionalProblem.get();
    }
    
    private Contest createContestIfNotExists(String name, String desc) {
        Optional<Contest> optionalContest = contestService.findOneByNameIgnoreCase(name);
        if (!optionalContest.isPresent()) {
            List<Problem> problems = problemService.getProblems();
            Contest contest = new Contest();
            contest.setDescription("Div2 159");            
            contest.setName("Div2 ");
            contest.setProblems(problems.subList(0, Math.min(3, problems.size())));
            contest.setStartTime(LocalDateTime.now());
            contest.setEndTime(LocalDateTime.now().plusDays(1));
            return contestService.createContest(contest);
        }
        return optionalContest.get();
    }

    private Submission createSubmissionIfNotExists() {
        Submission submission = new Submission();
        String gg = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
        // String gg = "dfssdf";
        submission.setCode(gg);
        System.out.println("saving submission");
        submissionRepository.save(submission);
        return submission;
    }
    
    @Override
    public void run(String... args) throws Exception {


        createContestantIfNotExists("yash@gmail.com");

        Problem problem = createProblemIfNotExists("Print Hello World", "Output string - Hello World", "", "Hello World");
        Problem problem1 = createProblemIfNotExists("Longest Increasing Subsequence", "Given an integer array nums, return the length of the longest strictly increasing subsequence.", "1 8 10 9 2 5 3 7 101 18", "4");

        // createSubmissionIfNotExists();
        
        // submissionService.save(submission);

        // List<Submission> ss = submissionRepository.findAll();
        // System.out.println(ss.get(0));

        // List<Problem> problems = new ArrayList<>();
        // problems.add(problem);
        
        // createContestIfNotExists("Div2 159", "Div2 159");

        
    }
}
