package com.example.contestplatform.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.contestplatform.model.Contest;
import com.example.contestplatform.model.Problem;
import com.example.contestplatform.model.Submission;

import lombok.extern.slf4j.Slf4j;

import com.example.contestplatform.model.Contestant;
import com.example.contestplatform.model.Score;

@Slf4j
@Component
public class JudgingWorker {
    @Autowired
    ProblemService problemService;

    @Autowired
    SubmissionService submissionService;

    @Autowired
    ContestService contestService;

    @Autowired
    ContestantService contestantService;

    @Autowired
    ScoreService scoreService;

    public void compileAndRun(Submission submission) {

        Optional<Problem> optionalProblem = problemService.getProblemById(submission.getProblemId());
        Problem problem = optionalProblem.get();

        Contestant contestant = contestantService.getById(submission.getUserId());

        Long contestId = submission.getContestId();
        Score score = null;
        if (contestId != null) {
            Optional<Contest> optionalContest = contestService.getContestById(contestId);
            Contest contest = optionalContest.get();
            score = scoreService.findOrCreateScore(contest, contestant);
        }

        try {
            ProcessBuilder builder = new ProcessBuilder();

            String filePath = "/tmp/Main" + submission.getId() + ".java";
            String folderPath = "/tmp";
            String fileName = "Main" + submission.getId() + ".java";
            String className = "Main" + submission.getId();

            String inputFilePath = "/tmp/input.txt";

            File codeDir = new File(filePath);
            File inputFile = new File(inputFilePath);

            // if (inputFile.createNewFile()) {
            //     System.out.println("File created: " + inputFile.getName());

                try {
                    FileWriter myWriter = new FileWriter(inputFilePath);
                    myWriter.write(problem.getInputFormat());
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            // } else {
            // System.out.println("File already exists.");
            // }

            
            try {
                FileWriter myWriter = new FileWriter(filePath);
                String code = submission.getCode();
                myWriter.write(code);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }


            // "\\wsl.localhost\Ubuntu-22.04\tmp\main.java"

            // docker run --rm -v /tmp:code openjdk bash -c javac /code/Main.java && java -cp /code Main 

            builder.command("docker", "run", "--rm", "-v", folderPath+":/code", "openjdk", 
                "bash", "-c", "javac /code/"+fileName+" && java -cp /code Main < /code/input.txt");

            // builder.command("docker", "run", "--rm", "-v", "/path/to/your/code:/code", "openjdk", 
            //     "bash", "-c", "javac /code/Main.java && java -cp /code Main < /code/input.txt");

            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line);
            }

            String output = outputBuilder.toString();
            String expectedOutput = problem.getOutputFormat();
            
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.info("Error executing the code");
                submission.setStatus("ERROR");
            } else if (output.equals(expectedOutput) == false) {
                log.info("Wrong answer");
                submission.setStatus("WRONG ANSWER");
            } else {
                log.info("Accepted");
                submission.setStatus("SUCCESS");
                if (score != null) {
                    score.updateScore(submission);
                }
            }

            log.info(output);
            submission.setCodeOutput(output);

        } catch (Exception e) {

            log.info("Error executing the code");
            submission.setStatus("ERROR");
        }

        submissionService.save(submission);
    }
}
