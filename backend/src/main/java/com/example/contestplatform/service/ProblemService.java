package com.example.contestplatform.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import com.example.contestplatform.model.Problem;
import com.example.contestplatform.repository.ProblemRepository;

@Service
public class ProblemService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ProblemRepository problemRepository;

    private static final String CACHE_PREFIX = "problemCache::";

    // @Cacheable(value = "problems")
    public List<Problem> getProblems() {
        return problemRepository.findAll();
    }

    public Problem save(Problem problem) {
        System.out.println("Adding Problem: id "+problem.getId()+" title: "+problem.getTitle());
        return problemRepository.save(problem);
    }

    // @Cacheable(value = "problemCache", key = "#id")
    public Optional<Problem> getProblemById(Long id) {
        List<Problem>problems = problemRepository.findAll();
        System.out.println("Fetching from database...");
        return problemRepository.findById(id);
    }

    // public Optional<Problem> getProblemById(Long id) {
    //     String cacheKey = CACHE_PREFIX + id;
        
    //     // Check if the problem exists in the Redis cache
    //     Problem cachedProblem = (Problem) redisTemplate.opsForValue().get(cacheKey);
    //     if (cachedProblem != null) {
    //         System.out.println("Fetching from Redis cache...");
    //         return Optional.of(cachedProblem);
    //     }

    //     // If not in cache, fetch from the database
    //     System.out.println("Fetching from database...");
    //     Optional<Problem> problem = problemRepository.findById(id);

    //     // If the problem is found, cache it in Redis with a TTL (optional)
    //     if (problem.isPresent()) {
    //         redisTemplate.opsForValue().set(cacheKey, problem.get(), 10, TimeUnit.MINUTES);
    //     }

    //     return problem;
    // }

    // public Optional<Problem> getProblemById(Long id) {

    //     String cacheKey = CACHE_PREFIX + id;

    //     Problem problem = new Problem();
    //     problem.setDescription("description");
    //     problem.setInputFormat("input");
    //     problem.setOutputFormat("output");
    //     redisTemplate.opsForValue().set(cacheKey, problem, 10, TimeUnit.MINUTES);

    //     Problem cachedProblem = (Problem) redisTemplate.opsForValue().get(cacheKey);
    //     return Optional.of(cachedProblem);
    // }
}
