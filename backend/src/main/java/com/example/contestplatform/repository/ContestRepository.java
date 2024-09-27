package com.example.contestplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.contestplatform.model.Contest;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    // Custom query methods (if needed)
}

