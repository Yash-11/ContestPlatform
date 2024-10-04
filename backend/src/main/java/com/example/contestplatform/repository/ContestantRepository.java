package com.example.contestplatform.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.contestplatform.model.Contestant;

@Repository
public interface ContestantRepository extends JpaRepository<Contestant, Long> {
    Optional<Contestant> findOneByUsernameIgnoreCase(String username);
}
