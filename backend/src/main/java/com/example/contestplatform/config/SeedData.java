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
        createContestantIfNotExists("naman@gmail.com");

        Problem problem1 = createProblemIfNotExists(
                "Print Hello World",
                "Output the string - Hello World",
                "1", // Test case 1, no additional input needed
                "Hello World");

        Problem problem2 = createProblemIfNotExists(
                "Longest Increasing Subsequence",
                "Given an integer array nums, return the length of the longest strictly increasing subsequence.",
                "1 10 1 8 10 9 2 5 3 7 101 18", // Test case 1, Array size 10, followed by elements
                "4");

        Problem problem3 = createProblemIfNotExists(
                "Sum of Two Numbers",
                "Given two integers, return their sum.",
                "1 2 5 10", // Test case 1, Array size 2 (the two numbers to sum)
                "15");

        Problem problem4 = createProblemIfNotExists(
                "Reverse a String",
                "Given a string, return the string in reverse order.",
                "1 5 hello", // Test case 1, String length 5 followed by string
                "olleh");

        Problem problem5 = createProblemIfNotExists(
                "Palindrome Check",
                "Given a string, check if it is a palindrome (reads the same backward and forward).",
                "1 5 madam", // Test case 1, String length 5 followed by string
                "true");

        Problem problem6 = createProblemIfNotExists(
                "Find the Maximum",
                "Given an integer array, return the maximum value in the array.",
                "1 5 1 8 5 3 7", // Test case 1, Array size 5 followed by elements
                "8");

        Problem problem7 = createProblemIfNotExists(
                "Count Vowels",
                "Given a string, return the number of vowels in the string.",
                "1 9 education", // Test case 1, String length 9 followed by string
                "5");

        Problem problem8 = createProblemIfNotExists(
                "Factorial of a Number",
                "Given a number, return the factorial of that number.",
                "1 1 5", // Test case 1, single number input
                "120");

        Problem problem9 = createProblemIfNotExists(
                "Fibonacci Series",
                "Given a number N, return the first N numbers of the Fibonacci sequence.",
                "1 1 7", // Test case 1, single number input
                "0 1 1 2 3 5 8");

        Problem problem10 = createProblemIfNotExists(
                "Check Prime",
                "Given a number, check if it is prime.",
                "1 1 29", // Test case 1, single number input
                "true");

        Problem problem11 = createProblemIfNotExists(
                "Greatest Common Divisor",
                "Given two integers, return their greatest common divisor (GCD).",
                "1 2 56 98", // Test case 1, Array size 2 (two integers)
                "14");

        Problem problem12 = createProblemIfNotExists(
                "Find the Second Largest Element",
                "Given an integer array, return the second largest element.",
                "1 6 10 4 9 6 2 7", // Test case 1, Array size 6 followed by elements
                "9");

        Problem problem13 = createProblemIfNotExists(
                "Count Occurrences of a Character",
                "Given a string and a character, return the number of occurrences of that character in the string.",
                "1 8 banana a", // Test case 1, String length 8 followed by string and the character to count
                "3");

        Problem problem14 = createProblemIfNotExists(
                "Sum of Array Elements",
                "Given an integer array, return the sum of its elements.",
                "1 5 1 2 3 4 5", // Test case 1, Array size 5 followed by elements
                "15");

        Problem problem15 = createProblemIfNotExists(
                "Check Armstrong Number",
                "Given a number, check if it is an Armstrong number (i.e., the sum of the cubes of its digits equals the number).",
                "1 1 153", // Test case 1, Single number input
                "true");

        Problem problem16 = createProblemIfNotExists(
                "Find the Missing Number",
                "Given an array of N-1 integers where the elements are in the range from 1 to N, return the missing number.",
                "1 4 1 2 4 5", // Test case 1, Array size 4 followed by elements
                "3");

        Problem problem17 = createProblemIfNotExists(
                "Check Anagram",
                "Given two strings, check if they are anagrams (contain the same characters with the same frequency).",
                "1 6 listen silent", // Test case 1, Two strings of equal length (6 characters)
                "true");

        Problem problem18 = createProblemIfNotExists(
                "Calculate Power of a Number",
                "Given two integers, base and exponent, return the value of base raised to the power of exponent.",
                "1 2 2 10", // Test case 1, Two integers: base and exponent
                "1024");

        Problem problem19 = createProblemIfNotExists(
                "Sort an Array",
                "Given an array of integers, return the array sorted in ascending order.",
                "1 5 5 3 8 1 4", // Test case 1, Array size 5 followed by elements
                "1 3 4 5 8");

        Problem problem20 = createProblemIfNotExists(
                "Find the Majority Element",
                "Given an array of integers, return the element that appears more than half the time (majority element).",
                "1 7 3 3 4 2 3 3 3", // Test case 1, Array size 7 followed by elements
                "3");

        // createSubmissionIfNotExists();

        // submissionService.save(submission);

        // List<Submission> ss = submissionRepository.findAll();
        // System.out.println(ss.get(0));

        // List<Problem> problems = new ArrayList<>();
        // problems.add(problem);

        // createContestIfNotExists("Div2 159", "Div2 159");

    }
}
