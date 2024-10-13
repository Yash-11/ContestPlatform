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
            
            Long submissionId = submission.getId();
            String filePath = "/tmp/Main" + submissionId + ".java";
            String folderPath = "/tmp";
            String fileName = "Main" + submissionId + ".java";
            String className = "Main" + submissionId;
            String inputFilePath = "/tmp/input_" + submissionId + ".txt";

            log.info("Starting code execution for submission ID: {}", submissionId);
            
            File codeFile = new File(filePath);
            File inputFile = new File(inputFilePath);


            try (FileWriter inputWriter = new FileWriter(inputFilePath)) {
                inputWriter.write(problem.getInputFormat());
                log.info("Successfully wrote input to file: {}", inputFilePath);
            } catch (IOException e) {
                log.error("Error writing input to file: {}", inputFilePath, e);
                submission.setStatus("ERROR");
                return;
            }

            try (FileWriter codeWriter = new FileWriter(filePath)) {
                codeWriter.write(submission.getCode());
                log.info("Successfully wrote code to file: {}", filePath);
            } catch (IOException e) {
                log.error("Error writing code to file: {}", filePath, e);
                submission.setStatus("ERROR");
                return;
            }


            // "\\wsl.localhost\Ubuntu-22.04\tmp\main.java"

            // docker run --rm -v /tmp:code openjdk bash -c javac /code/Main.java && java -cp /code Main 

            ProcessBuilder builder = new ProcessBuilder();
            builder.command("/usr/bin/docker", "run", "--rm", "-v", folderPath+":/code", "openjdk", 
                "bash", "-c", "javac /code/"+fileName+" && java -cp /code Main"+ " < /code/input_" + submissionId + ".txt");

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
                log.error("Error executing exitCode: "+exitCode);
                submission.setStatus("ERROR");
            } else if (output.equals(expectedOutput) == false) {
                log.info("Wrong answer");
                submission.setStatus("WRONG ANSWER");
            } else {
                log.info("Accepted");
                submission.setStatus("SUCCESS");
                if (score != null) {
                    score.updateScore(submission);
                    scoreService.save(score);
                }
            }

            log.debug("Code output for submission ID {}: {}", submissionId, output);
            submission.setCodeOutput(output);

            if (codeFile.delete()) {
                log.info("Deleted code file: {}", filePath);
            } else {
                log.error("Failed to delete code file: {}", filePath);
            }

            if (inputFile.delete()) {
                log.info("Deleted input file: {}", inputFilePath);
            } else {
                log.error("Failed to delete input file: {}", inputFilePath);
            }
        

        } catch (Exception e) {

            log.error("Unexpected error occurred during execution for submission ID: {}", submission.getId(), e);
            submission.setStatus("ERROR");
        }

        submissionService.save(submission);
    }
}
