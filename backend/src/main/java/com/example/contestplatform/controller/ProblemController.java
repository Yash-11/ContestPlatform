package com.example.contestplatform.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.contestplatform.model.Problem;
import com.example.contestplatform.repository.ProblemRepository;
import com.example.contestplatform.service.ProblemService;

@RestController
@RequestMapping("api/problems")
@CrossOrigin(origins = "http://localhost:3000")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public List<Problem> getProblems() {
        return problemService.getProblems();
    }

    // @PostMapping
    // public Problem createProblem(@RequestBody Problem problem) {
    //     return problemService.save(problem);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable Long id) {
        Optional<Problem> problem = problemService.getProblemById(id);

        return ResponseEntity.ok(problem.get());
    }
}


