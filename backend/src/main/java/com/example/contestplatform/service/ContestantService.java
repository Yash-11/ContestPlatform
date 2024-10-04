package com.example.contestplatform.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.contestplatform.dto.ContestantDTO;
import com.example.contestplatform.model.Contestant;
import com.example.contestplatform.repository.ContestantRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContestantService {
    @Autowired
    private ContestantRepository contestantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Contestant save(Contestant account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        log.info("Patient : "+account.getUsername()+" added");
        return contestantRepository.save(account);
    }

    public Contestant getById(Long id) {
        return contestantRepository.getReferenceById(id);
    }

    public Contestant registerContestant(ContestantDTO contestantDTO) {
        Contestant contestant = new Contestant();
        contestant.setUsername(contestantDTO.getUsername());
        contestant.setPassword(contestantDTO.getPassword());
        return save(contestant);
    } 

    public Optional<Contestant> findOneByUsernameIgnoreCase(String username) {
        return contestantRepository.findOneByUsernameIgnoreCase(username);
    }
}
