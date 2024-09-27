package com.example.contestplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.contestplatform.model.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
