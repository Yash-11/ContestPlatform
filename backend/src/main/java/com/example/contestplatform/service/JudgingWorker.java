package com.example.contestplatform.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.contestplatform.model.Problem;
import com.example.contestplatform.model.Submission;

@Component
public class JudgingWorker {
    @Autowired
    ProblemService problemService;

    @Autowired
    SubmissionService submissionService;

    public void compileAndRun(Submission submission) {

        Optional<Problem> optionalProblem = problemService.getProblemById(submission.getProblemId());
        Problem problem = optionalProblem.get();

        try {
            ProcessBuilder builder = new ProcessBuilder();

            String filePath = "/tmp/Main" + submission.getId() + ".java";
            String folderPath = "/tmp";
            String fileName = "Main" + submission.getId() + ".java";
            String className = "Main" + submission.getId();

            String inputFilePath = "/tmp/input.txt";

            File codeDir = new File(filePath);
            File inputFile = new File(inputFilePath);

            if (inputFile.createNewFile()) {
                System.out.println("File created: " + inputFile.getName());

                try {
                    FileWriter myWriter = new FileWriter(inputFilePath);
                    myWriter.write(problem.getInputFormat());
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            } else {
            System.out.println("File already exists.");
            }

            
            try {
                FileWriter myWriter = new FileWriter(filePath);
                myWriter.write(submission.getCode());
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
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // Compare output with expected output
                System.out.println("code run successful");
                System.out.println(output.toString());

                submission.setStatus("SUCCESS");
            } else {
                System.out.println("code run failed");
                System.out.println(output.toString());

                submission.setStatus("FAILED");
            }

            submission.setCodeOutput(output.toString());

        } catch (Exception e) {
            submission.setStatus("FAILED");
            submission.setCodeOutput("");
        }

        submissionService.save(submission);
    }
}
