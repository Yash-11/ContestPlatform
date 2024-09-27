package com.example.contestplatform.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.contestplatform.model.Contest;
import com.example.contestplatform.model.Problem;
import com.example.contestplatform.model.Submission;
import com.example.contestplatform.repository.SubmissionRepository;
import com.example.contestplatform.service.ContestService;
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
    private ContestService contestService;
    
    @Override
    public void run(String... args) throws Exception {
        Long g =Long.valueOf(89);
        Problem problem = new Problem();
        problem.setTitle("2sum");
        problem.setDescription("description");
        problem.setInputFormat("input");
        problem.setOutputFormat("output");
        // problem.setId(g);

        problemService.save(problem);

        Submission submission = new Submission();
        String gg = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
        // String gg = "dfssdf";
        submission.setCode(gg);
        submissionRepository.save(submission);
        // submissionService.save(submission);

        List<Submission> ss = submissionRepository.findAll();
        System.out.println(ss.get(0));

        // List<Problem> problems = new ArrayList<>();
        // problems.add(problem);
        List<Problem> problems = problemService.getProblems();

        Contest contest = new Contest();
        contest.setDescription("Div2 159");
        contest.setName("Div2 159");
        contest.setProblems(problems);
        contest.setStartTime(LocalDateTime.now());
        contest.setEndTime(LocalDateTime.now().plusDays(1));
        contestService.createContest(contest);

        
    }
}
