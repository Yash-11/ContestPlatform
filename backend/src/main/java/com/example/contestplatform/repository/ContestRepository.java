package com.example.contestplatform.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.contestplatform.model.Contest;
import com.example.contestplatform.model.Problem;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    Optional<Contest> findOneByNameIgnoreCase(String name);

    List<Contest> findByEndTimeAfter(LocalDateTime dateTime);
    List<Contest> findByEndTimeBefore(LocalDateTime dateTime);


}

