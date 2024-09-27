package com.example.contestplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.contestplatform.model.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}